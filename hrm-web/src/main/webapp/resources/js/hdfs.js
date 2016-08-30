var srcPath = '/';
var drawData;
function search(table, searchValue) {
    if (event.keyCode == 13) {
        reload(table, searchValue, srcPath);
    }
}

function reload(table, searchValue) {
    // limit and skip setting
    var tableAPI = table.api();
    var limit = tableAPI.settings()[0]._iDisplayLength;
    var skip = tableAPI.settings()[0]._iDisplayStart;

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
        // page length event
    }).on('length.dt', function () {
        reload($('#hdfs').dataTable(), $('#customSearch').val().trim());
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
    });

    var bindShortcutMove = function (fullPath) {
        var navigator = $('#path_shortcut');
        var shortcutPaths = fullPath.split("/");
        var trimPaths = [];
        for (var i = 0; i < shortcutPaths.length; i++) {
            if (shortcutPaths[i].length > 0) {
                trimPaths.push(shortcutPaths[i]);
            }
        }
        navigator.data('trimPaths', trimPaths);

        //패스정보가 없다면 기본으로 / 를 디스플레이한다.
        if (trimPaths.length == 0) {
            navigator.append('/');
        }
        for (var c = 0; c < trimPaths.length; c++) {

            var shortPath = trimPaths[c];
            var element = $('<a href="#" name="shortPath">' + trimPaths[c] + '</a>');
            element.data('index', c);
            element.css('float', 'left');
            element.click(function () {
                var data = navigator.data('trimPaths');
                var index = $(this).data('index');
                var newPath = '/';
                for (var g = 0; g <= index; g++) {
                    newPath = newPath + '/' + data[g];
                }
                srcPath = newPath;
                reload($('#hdfs').dataTable(), $('#customSearch').val().trim());
            });
            navigator.append('/');
            navigator.append(element);
        }
    };

    var bindStatusEvent = function (btn, data) {
        btn.click(function () {
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
            btn.click(function () {
                srcPath = data['fullyQualifiedPath'];
                reload($('#hdfs').dataTable(), $('#customSearch').val().trim());
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
});