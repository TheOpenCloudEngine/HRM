var srcPath = '/';
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
                // change init page setting (_iDisplayStart )
                table.settings()[0]._iDisplayStart = dataObj.displayStart;

                // make id edit href
                for (var i = 0; i < dataObj.data.length; i++) {
                    var icon = '<span class="glyphicon glyphicon-file"></span>';
                    if (dataObj.data[i]['directory']) {
                        icon = '<span class="glyphicon glyphicon-folder-open"></span>';
                    }
                    dataObj.data[i].filename = '<input type="checkbox" id="hdfs' + i + '" data-data="' +
                        JSON.stringify(dataObj.data[i]) + '"/>' + icon +
                        '<a href="#">' + dataObj.data[i].filename + '</a>';
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
            {data: 'path'}
        ]
    });

    // page event
    $('#hdfs').on('page.dt', function () {
        reload($('#hdfs').dataTable(), $('#customSearch').val().trim());
        // page length event
    }).on('length.dt', function () {
        reload($('#hdfs').dataTable(), $('#customSearch').val().trim());
    }).on('draw.dt', function () {
        console.log('Redraw occurred at: ' + new Date().getTime());
    });
});