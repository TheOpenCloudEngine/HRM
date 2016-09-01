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
    var tableAPI = $('#hdfs').dataTable().api();
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
                hdfsTable.settings()[0]._iDisplayStart = dataObj.displayStart;

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
    var renameModal = $('#renameModal');
    var ownerModal = $('#ownerModal');
    var permissionModal = $('#permissionModal');
    var deleteModal = $('#deleteModal');
    var modals = [newDirModal, uploadModal, renameModal, ownerModal, permissionModal, deleteModal];
    for (var i = 0; i < modals.length; i++) {
        bindModalCloseEvent(modals[i]);
    }
    $('#hdfs_newdir').click(function () {
        newDirModal.modal({show: true});
    });
    $('#hdfs_upload').click(function () {
        uploadModal.modal({show: true});
    });
    $('#hdfs_download').click(function () {
        var length = getSelectedFiles().length;
        if (length == 1) {
            downloadAction();
        } else if (length == 0) {
            msgBox('Select one file or one directory');
        } else if (length > 1) {
            msgBox('Select one file or one directory');
        }
    });
    $('#hdfs_rename').click(function () {
        var length = getSelectedFiles().length;
        if (length == 1) {
            renameModal.find('[name=name]').val(getSelectedFiles()[0]['filename']);
            renameModal.modal({show: true});
        } else if (length == 0) {
            msgBox('Select one file or one directory');
        } else if (length > 1) {
            msgBox('Select one file or one directory');
        }
    });
    $('#hdfs_owner').click(function () {
        var length = getSelectedFiles().length;
        if (length == 1) {
            ownerModal.find('[name=owner]').val(getSelectedFiles()[0]['owner']);
            ownerModal.find('[name=group]').val(getSelectedFiles()[0]['group']);
            ownerModal.modal({show: true});
        }
        else if (length > 0) {
            ownerModal.find('[name=owner]').val('');
            ownerModal.find('[name=group]').val('');
            ownerModal.modal({show: true});
        } else {
            msgBox('Select at least one more file or directory');
        }
    });
    $('#hdfs_permission').click(function () {
        var length = getSelectedFiles().length;
        if (length > 0) {
            permissionModal.find('[name=permission]').val('');
            permissionModal.modal({show: true});
        } else {
            msgBox('Select at least one more file or directory');
        }
    });
    $('#hdfs_delete').click(function () {
        var length = getSelectedFiles().length;
        if (length > 0) {
            deleteModal.modal({show: true});
        } else {
            msgBox('Select at least one more file or directory');
        }
    });

    newDirModal.find('[name=action]').click(function () {
        newDirModal.find('.close').click();
        var name = newDirModal.find('[name=name]').val().trim();
        if (name.length < 1) {
            return;
        }
        var path = srcPath + '/' + name;
        path.replace('//', '/');
        blockStart();
        $.ajax({
            type: "POST",
            url: "/rest/v1/hdfs/directory?path=" + path,
            data: '',
            dataType: "text",
            contentType: "application/json; charset=utf-8",
            success: function (response) {
                reload();
                blockStop();
                msgBox(path + ' Folder created');
            },
            error: function (request, status, errorThrown) {
                console.log(errorThrown);
                blockStop();
                msgBox('Failed to create folder ' + path);
            }
        });
    });

    renameModal.find('[name=action]').click(function () {
        renameModal.find('.close').click();
        var name = renameModal.find('[name=name]').val().trim();
        if (name.length < 1) {
            return;
        }
        var files = getSelectedFiles();
        var path = files[0]['fullyQualifiedPath'];

        blockStart();
        $.ajax({
            type: "PUT",
            url: "/rest/v1/hdfs/rename?path=" + path + '&rename=' + name,
            data: '',
            dataType: "text",
            contentType: "application/json; charset=utf-8",
            success: function (response) {
                reload();
                blockStop();
                msgBox(path + ' rename succeed');
            },
            error: function (request, status, errorThrown) {
                console.log(errorThrown);
                blockStop();
                msgBox(path + ' rename failed');
            }
        });
    });

    ownerModal.find('[name=action]').click(function () {
        ownerModal.find('.close').click();
        var owner = ownerModal.find('[name=owner]').val().trim();
        var group = ownerModal.find('[name=group]').val().trim();
        var recursive = ownerModal.find('[name=recursive]').prop('checked') ? 'true' : 'false';
        if (owner.length < 1 || group.length < 1) {
            return;
        }
        var files = getSelectedFiles();
        var paths = [];
        for (var i = 0; i < files.length; i++) {
            paths.push(files[i]['fullyQualifiedPath']);
        }
        var path = paths.join();

        blockStart();
        $.ajax({
            type: "PUT",
            url: "/rest/v1/hdfs/owner?path=" + path + '&owner=' + owner + '&group=' + group + '&recursive=' + recursive,
            data: '',
            dataType: "text",
            contentType: "application/json; charset=utf-8",
            success: function (response) {
                reload();
                blockStop();
                msgBox('Change owner succeed');
            },
            error: function (request, status, errorThrown) {
                console.log(errorThrown);
                blockStop();
                msgBox('Change owner failed');
            }
        });
    });

    permissionModal.find('[name=action]').click(function () {
        permissionModal.find('.close').click();
        var permission = permissionModal.find('[name=permission]').val();
        var recursive = permissionModal.find('[name=recursive]').prop('checked') ? 'true' : 'false';
        if (permission < 1) {
            return;
        }
        var files = getSelectedFiles();
        var paths = [];
        for (var i = 0; i < files.length; i++) {
            paths.push(files[i]['fullyQualifiedPath']);
        }
        var path = paths.join();

        blockStart();
        $.ajax({
            type: "PUT",
            url: "/rest/v1/hdfs/permission?path=" + path + '&permission=' + permission + '&recursive=' + recursive,
            data: '',
            dataType: "text",
            contentType: "application/json; charset=utf-8",
            success: function (response) {
                reload();
                blockStop();
                msgBox('Change permission succeed');
            },
            error: function (request, status, errorThrown) {
                console.log(errorThrown);
                blockStop();
                msgBox('Change permission failed');
            }
        });
    });

    deleteModal.find('[name=action]').click(function () {
        deleteModal.find('.close').click();
        var files = getSelectedFiles();
        var paths = [];
        for (var i = 0; i < files.length; i++) {
            paths.push(files[i]['fullyQualifiedPath']);
        }
        var path = paths.join();

        blockStart();
        $.ajax({
            type: "DELETE",
            url: "/rest/v1/hdfs/file?path=" + path,
            data: '',
            dataType: "text",
            contentType: "application/json; charset=utf-8",
            success: function (response) {
                reload();
                blockStop();
                msgBox('Delete files succeed');
            },
            error: function (request, status, errorThrown) {
                console.log(errorThrown);
                blockStop();
                msgBox('Delete files failed');
            }
        });
    });

    uploadModal.find('[name=action]').click(function () {
        var _file = document.getElementById('uploadfile');
        if (_file.files.length === 0) {
            return;
        }
        var form = uploadModal.find('form');
        form.find('[name=dir]').val(srcPath);

        uploadModal.find('.close').click();
        var progressPanel = $('#progressPanel');
        var progressBar = progressPanel.find('.progress-bar');
        var progressTitle = progressPanel.find('.progress-title');
        progressBar.css('width', '0%');
        progressTitle.html('Uploading - 0%');

        //var formData = new FormData();
        //formData.append('file', _file.files[0]);
        //formData.append('dir', srcPath);
        //formData.append('uuid', uuid);

        var bar = $('.bar');
        var percent = $('.percent');
        var status = $('#status');

        form.ajaxSubmit({
            beforeSend: function() {
                progressPanel.show();
            },
            uploadProgress: function(event, position, total, percentComplete) {
                var percentVal = percentComplete + '%';
                progressBar.css('width', percentVal);
                progressTitle.html('Uploading - ' + percentVal);
            },
            success: function() {
                msgBox('File upload succeed');
            },
            complete: function() {
                progressPanel.hide();
                reload();
            }
        });

        //$.ajax({
        //    url: '/hdfs/upload',
        //    processData: false,
        //    contentType: false,
        //    type: 'POST',
        //    data: formData,
        //    dataType: 'json',
        //    beforeSend: function () {
        //        progressPanel.show();
        //    },
        //    success: function (result) {
        //        msgBox('File upload succeed');
        //    },
        //    error: function (e) {
        //        msgBox('File upload failed');
        //    },
        //    complete: function () {
        //        //clearInterval(interval);
        //        progressPanel.hide();
        //        reload();
        //    }
        //});

        //var interval = setInterval(function () {
        //    $.ajax({
        //        type: "GET",
        //        url: "/hdfs/upload/progress?uuid=" + uuid,
        //        data: '',
        //        dataType: "text",
        //        contentType: "application/json; charset=utf-8",
        //        success: function (response) {
        //            console.log(response);
        //            var map = JSON.parse(response);
        //            if (map.status) {
        //                progressBar.css('width', map.status + '%');
        //                progressTitle.html('Uploading - ' + map.status + '%');
        //            }
        //        }
        //    });
        //}, 1000);
    });

    var downloadAction = function () {
        var file = getSelectedFiles()[0];
        var path = file['fullyQualifiedPath'];
        $.fileDownload('/rest/v1/hdfs/file?path=' + path, {
            preparingMessageHtml: "We are preparing your file, please wait...",
            failMessageHtml: "There was a problem generating your file, please try again."
        });
    };

});