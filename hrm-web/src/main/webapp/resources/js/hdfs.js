var srcPath = '/';
var drawData;
var hdfsTable;
var hdfsTableDiv;
function search() {
    if (event.keyCode == 13) {
        reload(true);
    }
}

function reload(refresh) {
    var searchValue = $('#customSearch').val().trim();
    blockStart();
    // limit and skip setting
    var tableAPI = hdfsTable.api();
    var limit = tableAPI.settings()[0]._iDisplayLength;
    var skip = tableAPI.settings()[0]._iDisplayStart;
    if (refresh) {
        skip = 0;
    }

    tableAPI.ajax.url('/hdfs/list?limit=' + limit + '&skip=' + skip + '&filter=' + searchValue + '&path=' + srcPath);
    tableAPI.ajax.reload();
}

$(document).ready(function () {
    hdfsTableDiv = $('#hdfs');
    hdfsTable = hdfsTableDiv.DataTable({
        serverSide: true,
        searching: false,
        ajax: {
            url: '/hdfs/list?path=' + srcPath,
            dataSrc: function (dataObj) {
                drawData = JSON.parse(JSON.stringify(dataObj.data));
                table.settings()[0]._iDisplayStart = dataObj.displayStart;

                // make id edit href
                for (var i = 0; i < dataObj.data.length; i++) {
                    var icon = '<span class="glyphicon glyphicon-file"></span>';
                    if (dataObj.data[i]['directory']) {
                        icon = '<span class="glyphicon glyphicon-folder-open"></span>';
                    }
                    dataObj.data[i].filename = '<input type="checkbox" name="hdfsobj" data-data="' +
                        JSON.stringify(dataObj.data[i]) + '"/>&nbsp;' + icon +
                        '&nbsp;<a href="#">' + dataObj.data[i].filename + '</a>';

                    dataObj.data[i].status = '<button class="btn btn-xs rounded btn-primary" name="statusBtn">Detail</button>'
                }
                return dataObj.data;
            }
        },
        columns: [
            {data: 'filename'},
            {data: 'length'},
            {data: 'owner'},
            {data: 'group'},
            {data: 'permission'},
            {data: 'status'}
        ]
    });

    // page event
    hdfsTableDiv.on('page.dt', function () {
        reload();
    }).on('length.dt', function () {
        reload();
    }).on('error.dt', function (e, settings, techNote, message) {
        console.log('An error has been reported by DataTables: ', message);
        blockStop();
    }).on('draw.dt', function () {

        bindShortcutMove(srcPath);

        var hdfsObjs = $("[name=hdfsobj]");
        hdfsObjs.each(function (index, check) {
            var checkbox = $(check);
            var td = checkbox.parent();
            var tr = td.parent();
            var data = drawData[index];
            var statusBtn = tr.find('[name=statusBtn]');
            bindStatusEvent(statusBtn, data);

            var filenameBtn = td.find('a');
            folderClickEvent(filenameBtn, data);

            trClickEvent(tr, data);
        });
        blockStop();
    });

    var bindShortcutMove = function (fullPath) {
        var navigator = $('#path_shortcut');
        navigator.html('');
        var shortcutPaths = fullPath.split("/");
        var trimPaths = [];
        for (var i = 0; i < shortcutPaths.length; i++) {
            if (shortcutPaths[i].length > 0) {
                trimPaths.push(shortcutPaths[i]);
            }
        }
        navigator.data('trimPaths', trimPaths);

        //루트패스정보로 이동하는 로직을 제공한다.
        var rootElement = $('<a href="#" name="shortPath">/..</a>');
        rootElement.click(function () {
            srcPath = '/';
            reload(true);
        });
        navigator.append(rootElement);

        //패스정보가 없다면 기본으로 / 를 디스플레이한다.
        if (trimPaths.length == 0) {
            navigator.append('/');
        }

        //패스정보를 클릭시 해당 폴더로 이동하도록 한다.
        for (var c = 0; c < trimPaths.length; c++) {
            var shortPath = trimPaths[c];
            var element = $('<a href="#" name="shortPath">' + trimPaths[c] + '</a>');
            element.data('index', c);
            element.click(function () {
                var data = navigator.data('trimPaths');
                var index = $(this).data('index');
                var newPath = '';
                for (var g = 0; g <= index; g++) {
                    newPath = newPath + '/' + data[g];
                }
                srcPath = newPath;
                reload(true);
            });
            navigator.append('/');
            navigator.append(element);
        }


        //TODO
        /**
         * ajax 로딩 .done
         * 폴더 이동시 페이지 초기화
         * 버튼 클릭시 체크박스 클릭 방지
         * 5. 이름변경 삭제 내려받기 복사 메뉴
         * 6. 새 파일 업로드 메뉴
         */
    };

    var bindStatusEvent = function (btn, data) {
        btn.click(function (event) {
            event.stopPropagation()
            var modal = $('#statusModal');
            modal.modal({
                show: true
            });
            modal.find('[name=close]').click(function () {
                $('#statusModal').find('.close').click();
            });

            var str = JSON.stringify(data, null, 2);
            modal.find('[name=body]').val(str);
        });
    };

    var folderClickEvent = function (btn, data) {
        if (data['directory']) {
            btn.click(function (event) {
                event.stopPropagation()
                srcPath = data['fullyQualifiedPath'];
                reload(true);
            });
        }
    };

    var trClickEvent = function (tr, data) {
        tr.click(function () {
            var checkbox = tr.find('input:checkbox');
            if (checkbox.prop('checked')) {
                checkbox.prop('checked', false);
            } else {
                checkbox.prop('checked', true);
            }
        })
    };

    var getSelectedFiles = function () {
        var selectedFiles = [];
        var hdfsObjs = $("[name=hdfsobj]");
        hdfsObjs.each(function (index, check) {
            var checkbox = $(check);
            if (checkbox.prop('checked')) {
                selectedFiles.push(drawData[index]);
            }
        });
        return selectedFiles;
    };

    var bindModalCloseEvent = function (modal) {
        modal.find('[name=close]').click(function () {
            modal.find('.close').click();
        });
    };

    var newDirModal = $('#newDirModal');
    var uploadModal = $('#uploadModal');
    var downloadModal = $('#downloadModal');
    var renameModal = $('#renameModal');
    var ownerModal = $('#ownerModal');
    var permissionModal = $('#permissionModal');
    var deleteModal = $('#deleteModal');
    var modals = [newDirModal, uploadModal, downloadModal, renameModal, ownerModal, permissionModal, deleteModal];
    for (var i = 0; i < modals.length; i++) {
        bindModalCloseEvent(modals[i]);
    }
    $('#hdfs_newdir').click(function () {newDirModal.modal({show: true});});
    $('#hdfs_upload').click(function () {uploadModal.modal({show: true});});
    $('#hdfs_download').click(function () {downloadModal.modal({show: true});});
    $('#hdfs_rename').click(function () {renameModal.modal({show: true});});
    $('#hdfs_owner').click(function () {ownerModal.modal({show: true});});
    $('#hdfs_permission').click(function () {permissionModal.modal({show: true});});
    $('#hdfs_delete').click(function () {deleteModal.modal({show: true});});

    newDirModal.find('[name=action]').click(function () {
        var name = newDirModal.find('[name=name]').val().trim();
        if (name.length < 1) {
            return;
        }
        blockStart();
        $.ajax({
            type: "POST",
            url: "/rest/v1/hdfs/directory?path=" + srcPath + '/' + name,
            data: '',
            dataType: "text",
            contentType: "application/json; charset=utf-8",
            success: function (response) {
                reload();
                blockStop();
            },
            error: function (request, status, errorThrown) {
                console.log(errorThrown);
                blockStop();
            }
        });
    });

});