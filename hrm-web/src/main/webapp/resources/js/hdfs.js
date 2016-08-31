var srcPath = '/';
var drawData;
function search(table, searchValue) {
    if (event.keyCode == 13) {
        reload(table, searchValue, true);
    }
}

function reload(table, searchValue, refresh) {
    blockStart();
    // limit and skip setting
    var tableAPI = table.api();
    var limit = tableAPI.settings()[0]._iDisplayLength;
    var skip = tableAPI.settings()[0]._iDisplayStart;
    if (refresh) {
        skip = 0;
    }

    tableAPI.ajax.url('/hdfs/list?limit=' + limit + '&skip=' + skip + '&filter=' + searchValue + '&path=' + srcPath);
    tableAPI.ajax.reload();
}

$(document).ready(function () {
    var table = $('#hdfs').DataTable({
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
    $('#hdfs').on('page.dt', function () {
        reload($('#hdfs').dataTable(), $('#customSearch').val().trim());
    }).on('length.dt', function () {
        reload($('#hdfs').dataTable(), $('#customSearch').val().trim());
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
            reload($('#hdfs').dataTable(), $('#customSearch').val().trim(), true);
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
                reload($('#hdfs').dataTable(), $('#customSearch').val().trim(), true);
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
                reload($('#hdfs').dataTable(), $('#customSearch').val().trim(), true);
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

    $('#hdfs_newdir').click(function () {

        $.ajax({
            type: "POST",
            url: "/rest/v1/hdfs/directory",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "text",
            async: false,
            success: function (response) {
                var res = JSON.parse(response);

                //로그인 세션 확인시 어세스 토큰을 서버로 보냄
                if (res.success && res.map.validated) {
                    userName = res.map.userName;
                    checkScopeToken();
                }
                //로그인 세션 만료시 로그인 URL 노출
                else {
                    viewLogin();
                }
            },
            error: function (request, status, errorThrown) {

            }
        });
    });

    //hdfs_newdir" class="btn-u btn-u-blue" type="button">New Folder</button>
    //<button id="hdfs_upload" class="btn-u btn-u-dark-blue" type="button">Upload</button>
    //    <button id="hdfs_download" class="btn-u btn-u-default" type="button">Download</button>
    //    <button id="hdfs_rename" class="btn-u btn-u-aqua" type="button">Rename</button>
    //    <button id="hdfs_delete
});