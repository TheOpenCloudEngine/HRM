$(function () {

    var template = {
        complexTemplate: function () {
            return '' +
                '<div class="form-group">' +
                '<label class="col-md-3 control-label template-label" data-toggle="tooltip" title=""> </label>' +
                '<div class="col-md-8 item-space">' +
                '</div>' +
                '<div class="col-md-1">' +
                '<button type="button" class="btn-u btn-u-default pull-right addBtn">Add</button>' +
                '</div>' +
                '</div>'
        },
        textTemplate: function () {
            return '' +
                '<div class="form-group">' +
                '<label class="col-md-3 control-label template-label"> </label>' +
                '<div class="col-md-9">' +
                '<input name="" type="text" class="form-control value-space" placeholder="Text">' +
                '</div>' +
                '</div>'
        },
        textAreaTemplate: function () {
            return '' +
                '<div class="form-group">' +
                '<label class="col-md-3 control-label template-label"> </label>' +
                '<div class="col-md-9">' +
                '<textarea name="" rows="8" class="form-control value-space" placeholder="script"></textarea>' +
                '</div>' +
                '</div>'
        },
        numberTemplate: function () {
            return '' +
                '<div class="form-group">' +
                '<label class="col-md-3 control-label template-label"> </label>' +
                '<div class="col-md-9">' +
                '<input name="" type="number" class="form-control value-space" placeholder="Number">' +
                '</div>' +
                '</div>'
        },
        booleanTemplate: function () {
            return '' +
                '<div class="form-group">' +
                '<label class="col-md-3 control-label template-label"> </label>' +
                '<div class="col-md-9">' +
                '<label class="checkbox"><input class="value-space" type="checkbox" name="">check</label>' +
                '</div>' +
                '</div>'
        },
        collectionTemplate: function () {
            return '' +
                '<li class="list-group-item collection">' +
                '<a href="JavaScript:void(0)" class="coll-name"></a>' +
                '<i class="fa fa-bars coll-caller" style="cursor:s-resize;position: absolute;right: 5px;top: 12px;"></i>' +
                '<ul class="list-group sidebar-nav-v1 coll-menu" style="display:none;position: absolute;right: 0%;top: 100%;">' +
                '<li class="list-group-item">' +
                '<a href="JavaScript:void(0)" class="coll-edit"><i class="glyphicon glyphicon-pencil"></i>Edit</a>' +
                '</li>' +
                '<li class="list-group-item">' +
                '<a href="JavaScript:void(0)" class="coll-copy"><i class="glyphicon glyphicon-tags"></i>Duplicate</a>' +
                '</li>' +
                '<li class="list-group-item">' +
                '<a href="JavaScript:void(0)" class="coll-del"><i class="glyphicon glyphicon-trash"></i>Delete</a>' +
                '</li>' +
                '</ul>' +
                '</li>'
        },
        textListItem: function () {
            return '' +
                '<div class="row template-item" style="margin-bottom: 10px;">' +
                '<div class="col-md-10 col-sm-10">' +
                '<input type="text" name="" class="form-control" placeholder="Text">' +
                '</div>' +
                '<div class="col-md-1 col-sm-1">' +
                '<button type="button" class="btn-u btn-u-default pull-right delBtn">Del</button>' +
                '</div>' +
                '</div>'
        },
        textareaListItem: function () {
            return '' +
                '<div class="row template-item" style="margin-bottom: 10px;">' +
                '<div class="col-md-10 col-sm-10">' +
                '<textarea name="" rows="8" class="form-control" placeholder=""></textarea>' +
                '</div>' +
                '<div class="col-md-1 col-sm-1">' +
                '<button type="button" class="btn-u btn-u-default pull-right delBtn">Del</button>' +
                '</div>' +
                '</div>'
        },
        mapItem: function () {
            return '' +
                '<div class="row template-item" style="margin-bottom: 10px;">' +
                '<div class="col-md-5 col-sm-5">' +
                '<input type="text" name="" class="form-control keytab" placeholder="key">' +
                '</div>' +
                '<div class="col-md-5 col-sm-5">' +
                '<input type="text" name="" class="form-control valuetab" placeholder="value">' +
                '</div>' +
                '<div class="col-md-1 col-sm-1">' +
                '<button type="button" class="btn-u btn-u-default pull-right delBtn">Del</button>' +
                '</div>' +
                '</div>'
        },
        newComplexItem: function (field, type) {
            var me = this;
            var item;
            if (type == 'textList') {
                item = $(me.textListItem());
            }
            else if (type == 'textareaList') {
                item = $(me.textareaListItem());
            }
            else if (type == 'map') {
                item = $(me.mapItem());
            } else {
                return;
            }
            var itemSpace = field.find('.item-space');
            if (itemSpace.find('template-item').length) {
                itemSpace.find('template-item').last().after(item);
            } else {
                itemSpace.append(item);
            }
            item.find(".delBtn").click(function () {
                item.remove();
            });
            return item;
        },
        createComplexField: function (element, type, data) {
            var me = this;
            var field = $(me.complexTemplate());
            field.find('.addBtn').click(function () {
                me.newComplexItem(field, type);
            });
            element.append(field);
            if (type == 'textList') {
                if (!data || !data.length) {
                    me.newComplexItem(field, type);
                } else {
                    for (var i = 0; i < data.length; i++) {
                        var complexItem = me.newComplexItem(field, type);
                        complexItem.find('input').val(data[i])
                    }
                }
            }
            else if (type == 'textareaList') {
                if (!data || !data.length) {
                    me.newComplexItem(field, type);
                } else {
                    for (var i = 0; i < data.length; i++) {
                        var complexItem = me.newComplexItem(field, type);
                        complexItem.find('textarea').val(data[i])
                    }
                }
            }
            else if (type == 'map') {
                if (!data || $.isEmptyObject(data)) {
                    me.newComplexItem(field, type);
                } else {
                    for (var key in data) {
                        var complexItem = me.newComplexItem(field, type);
                        complexItem.find('.keytab').val(key);
                        complexItem.find('.valuetab').val(data[key]);
                    }
                }
            }
            return field;
        },
        createTextField: function (element, data) {
            var me = this;
            var field = $(me.textTemplate());
            element.append(field);
            if (!controller.emptyString(data)) {
                field.find('.value-space').val(data);
            }
            return field;
        },
        createTextAreaField: function (element, data) {
            var me = this;
            var field = $(me.textAreaTemplate());
            element.append(field);
            if (!controller.emptyString(data)) {
                field.find('.value-space').val(data);
            }
            return field;
        },
        createNumberField: function (element, data) {
            var me = this;
            var field = $(me.numberTemplate());
            element.append(field);
            if ($.isNumeric(data)) {
                field.find('.value-space').val(data);
            }
            return field;
        },
        createBooleanField: function (element, data) {
            var me = this;
            var field = $(me.booleanTemplate());
            element.append(field);
            if (data) {
                field.find('.value-space').prop('checked', true);
            }
            return field;
        },
        createField: function (element, data, formData) {
            if (!formData) {
                formData = {};
            }
            var me = this;
            var name = data.name;
            var type = data.type;
            var description = data.description;
            var field;
            if (type == 'textList' || type == 'map' || type == 'textareaList') {
                field = me.createComplexField(element, type, formData[name]);
                field.data('data', data);
            } else if (type == 'text') {
                field = me.createTextField(element, formData[name]);
                field.data('data', data);
            } else if (type == 'number') {
                field = me.createNumberField(element, formData[name]);
                field.data('data', data);
            } else if (type == 'boolean') {
                field = me.createBooleanField(element, formData[name]);
                field.data('data', data);
            } else if (type == 'textarea') {
                field = me.createTextAreaField(element, formData[name]);
                field.data('data', data);
            }
            if (field) {
                var descriptionTooltip = '';
                var split = description.split('\n');
                for (var i = 0; i < split.length; i++) {
                    split[i] = split[i].replace(/</gi, "&lt;").replace(/>/gi, "&gt;")
                    descriptionTooltip += '<div style="text-align: left;font-size: 14px;">' + split[i] + '</div>';
                }
                var label = field.find('.template-label');
                label.html(name);
                label.attr('title', descriptionTooltip);
                label.tooltip({
                    html: true,
                    'container': 'body'
                });

                if (field.find('.value-space').length) {
                    field.find('.value-space').attr('name', data.name);
                }

                if (name == 'clientJobId' || name == 'clientJobName') {
                    field.find('.value-space').attr('readonly', true);
                }
            }
        },
        createCollection: function (element, data) {
            var me = this;
            var collection = $(me.collectionTemplate());
            element.append(collection);
            collection.find('.coll-name').html(data['jobName']);
            var collMenu = collection.find('.coll-menu');
            collection.find('.coll-caller').click(function (event) {
                if (collMenu.css('display') == 'none') {
                    $('.collection').css('z-index', 0).find('.coll-menu').hide();
                    collection.css('z-index', 1);
                    collMenu.show();
                } else {
                    collMenu.hide();
                }
            });
            return collection;
        }
    };

    var controller = {
        //baseUrl: 'http://52.78.88.87:8080',
        //baseUrl: 'http://localhost:8080',
        baseUrl: '',
        maxTab: 7,
        async: true,
        metadata: {},
        init: function () {
            var me = this;
            me.async = false;
            me.drawFields();
            me.drawCollections();
            me.async = true;
            me.drawHistories();

            $('#history-tab-plus').find('a').click(function () {
                me.createHistoryTab({}).find('a').click();
            });
            $('#sendBtn').click(function () {
                me.send();
            });
            $('#saveAsBtn').click(function () {
                me.saveAs();
            });
            $('#saveBtn').click(function () {
                me.save();
            });
            $('#status-kill').click(function () {
                me.kill();
            });

            $('.modal').each(function () {
                var modal = $(this);
                modal.find('[name=close]').click(function () {
                    modal.find('.close').click();
                });
            });

            $('.job-status').hide();

            var logArea = $('#logArea');
            var detailArea = $('#detailArea');
            var killArea = $('#killArea');
            var logBtn = $('#job-log-btn');
            var detailBtn = $('#job-detail-btn');
            var killBtn = $('#job-kill-btn');
            logBtn.click(function () {
                logBtn.addClass('active');
                detailBtn.removeClass('active');
                killBtn.removeClass('active');
                logArea.parent().parent().show();
                detailArea.parent().parent().hide();
                killArea.parent().parent().hide();
            });
            detailBtn.click(function () {
                logBtn.removeClass('active');
                detailBtn.addClass('active');
                killBtn.removeClass('active');
                logArea.parent().parent().hide();
                detailArea.parent().parent().show();
                killArea.parent().parent().hide();
            });
            killBtn.click(function () {
                logBtn.removeClass('active');
                detailBtn.removeClass('active');
                killBtn.addClass('active');
                logArea.parent().parent().hide();
                detailArea.parent().parent().hide();
                killArea.parent().parent().show();
            });

            $('#curlBtn').click(function () {
                me.openCurl();
            });

            me.intervalTrigger();
        },
        /**
         * 폼의 정보를 취합하여 curl 명령어로 변환한다.
         */
        openCurl: function () {
            var me = this;
            var formData = me.getFormData();
            var modal = $('#curlModal');
            modal.find('[name=body]').val('');
            $.ajax({
                type: "POST",
                url: "/eco/clientJob/curl?jobType=" + jobType,
                data: JSON.stringify(formData),
                dataType: "text",
                contentType: "application/json; charset=utf-8",
                success: function (response) {
                    modal.find('[name=body]').val(response);
                    modal.modal({
                        show: true
                    });
                },
                error: function (request, status, errorThrown) {
                    msgBox('Failed to convert curl');
                }
            });
        },
        /**
         * 현재 활성화되어있는 탭의 잡아이디가 존재한다면 클라이언트잡을 불러와서
         * 잡 디테일에 데이터를 표기해주는 배치 잡.
         */
        intervalTrigger: function () {
            var me = this;
            var clientJobId;
            var interval = function () {
                console.log('intervalTrigger');
                var activeTab = me.getActiveTab();
                if (activeTab && activeTab.data('clientJob')) {
                    clientJobId = activeTab.data('clientJob')['clientJobId'];
                    $.ajax({
                        type: "GET",
                        url: "/rest/v1/clientJob/job/" + clientJobId,
                        data: '',
                        dataType: "text",
                        contentType: "application/json; charset=utf-8",
                        success: function (response) {
                            activeTab.data('clientJob', JSON.parse(response));
                            me.updateFormDetail(JSON.parse(response));
                        },
                        error: function (request, status, errorThrown) {

                        },
                        complete: function () {
                            setTimeout(function () {
                                interval();
                            }, 1000);
                        }
                    });
                } else {
                    setTimeout(function () {
                        interval();
                    }, 1000);
                }
            };
            interval();
        },
        emptyString: function (value) {
            if (typeof value == 'undefined') {
                return true;
            }
            if (!value) {
                return true;
            }
            if (
                (value.length == 0)
                ||
                (value == "")
                ||
                (value.replace(/\s/g, "") == "")
                ||
                (!/[^\s]/.test(value))
                ||
                (/^\s*$/.test(value))
            ) {
                return true;
            }
            return false;
        },
        kill: function () {
            var me = this;
            var activeTab = me.getActiveTab();
            if (activeTab && activeTab.data('clientJob')) {
                var clientJobId = activeTab.data('clientJob')['clientJobId'];
                blockStart();
                $.ajax({
                    type: "DELETE",
                    url: "/rest/v1/clientJob/kill/" + clientJobId,
                    data: '',
                    dataType: "text",
                    contentType: "application/json; charset=utf-8",
                    success: function (response) {
                        me.updateFormDetail(JSON.parse(response));
                    },
                    error: function (request, status, errorThrown) {

                    },
                    complete: function () {
                        blockStop();
                    }
                });
            }
        }
        ,
        /**
         * 폼 데이터를 서버로 전송해 잡을 실행한다.
         * 잡을 실행한 후에 되돌아오는 잡아이디를 탭 데이터로 저장한다.
         * 탭 데이터를 저장후 폼 디테일 업데이트를 한번 실행하도록 한다.
         */
        send: function () {
            var me = this;
            var formData = this.getFormData();
            delete formData['clientJobId'];
            blockStart();
            $.ajax({
                type: "POST",
                url: "/rest/v1/clientJob/" + jobType + "?executeFrom=" + "console",
                data: JSON.stringify(formData),
                dataType: "text",
                contentType: "application/json; charset=utf-8",
                success: function (response) {
                    blockStop();
                    me.updateTabAsClientJob(me.getActiveTab(), JSON.parse(response));
                    me.updateFormDetail(JSON.parse(response));
                },
                error: function (request, status, errorThrown) {
                    msgBox('Failed to run job.');
                    blockStop();
                }
            });
        }
        ,
        /**
         * 현재 폼의 내용을 콜렉셕에 저장한다.
         * 현재 폼의 jobName 이 콜렉션 리스트에 존재한다면 오버라이트한다.
         * 현재 폼의 jobName 이 콜렉션 리스트에 없다면 새로이 저장한다.
         * 폼의 clientJobId 는 저장하지 않는다.
         */
        save: function () {
            var me = this;
            var formData = me.getFormData();
            var clientJobName = formData['clientJobName'];
            var collection = me.selectCollectionByName(clientJobName);
            if (collection) {
                //데이터 무결성을 위한 오브젝트 카피
                var data = JSON.parse(JSON.stringify(collection.data('data')));
                delete formData['clientJobId'];
                eval('data["' + jobType + 'Request"] = formData');
                blockStart();
                $.ajax({
                    type: "PUT",
                    url: "/rest/v1/collection/" + data['_id'],
                    data: JSON.stringify(data),
                    dataType: "text",
                    contentType: "application/json; charset=utf-8",
                    success: function (response) {
                        blockStop();
                        me.drawCollections();
                    },
                    error: function (request, status, errorThrown) {
                        if (request.status === 409) {
                            msgBox(name + ' is already exist.');
                        } else {
                            msgBox('Failed to update collection.');
                        }
                        blockStop();
                    }
                });
            } else {
                me.saveAs();
            }
        },
        /**
         * 현재 폼의 내용을 새 이름의 콜렉션으로 저장한다.
         */
        saveAs: function () {
            var me = this;
            var data = {
                jobType: jobType
            };
            var formData = me.getFormData();
            delete formData['clientJobId'];
            var modal = $('#saveAsModal');
            modal.find('[name=name]').val('');
            if (!me.emptyString(formData['clientJobName'])) {
                modal.find('[name=name]').val(formData['clientJobName']);
            }
            modal.find('[name=action]').unbind('click');
            modal.find('[name=action]').click(function () {
                modal.find('.close').click();
                var name = modal.find('[name=name]').val().trim();
                if (name.length < 1) {
                    return;
                }
                formData['clientJobName'] = name;
                eval('data["' + jobType + 'Request"] = formData;');
                data['jobName'] = name;
                blockStart();
                $.ajax({
                    type: "POST",
                    url: "/rest/v1/collection",
                    data: JSON.stringify(data),
                    dataType: "text",
                    contentType: "application/json; charset=utf-8",
                    success: function (response) {
                        blockStop();
                        me.updateTabName(me.getActiveTab(), name);
                        me.drawCollections();
                    },
                    error: function (request, status, errorThrown) {
                        if (request.status === 409) {
                            msgBox(name + ' is already exist.');
                        } else {
                            msgBox('Failed to create collection.');
                        }
                        blockStop();
                    }
                });
            });
            modal.modal({
                show: true
            });
        },
        getAlltabs: function () {
            return $('.history-tab');
        },
        /**
         * 현재 선택된 탭을 반환한다.
         * 탭 클릭시 적용되는 액티브 데이터가 우선이며, 액티브 데이터를 가진 탭을 찾을 수 없는경우 css 에 의해 선별한다.
         * @returns {*}
         */
        getActiveTab: function () {
            var tabs = this.getAlltabs();
            var selectedTab;
            tabs.each(function () {
                if ($(this).data('active')) {
                    selectedTab = $(this);
                }
            });
            if (!selectedTab) {
                return $('.history-tab.active');
            } else {
                return selectedTab;
            }
        },
        /**
         * 탭 중에서 주어진 이름과 같은 탭을 반환한다.
         * @param name
         * @returns {*}
         */
        selectTabByName: function (name) {
            var tabs = this.getAlltabs();
            var match = [];
            tabs.each(function () {
                if ($(this).find('a').html() == name) {
                    match.push($(this));
                }
            });
            return match;
        },
        /**
         * 현재 활성화되어있는 탭의 잡아이디가 데이터와 일치한다면 폼의 잡디테일을 업데이트한다.
         * @param clientJob
         */
        updateFormDetail: function (clientJob) {
            if (!clientJob || !clientJob['clientJobId']) {
                return;
            }
            var me = this;
            var activeTab = me.getActiveTab();
            if (activeTab.data('clientJob')) {
                var data = activeTab.data('clientJob');
                if (data['clientJobId'] == clientJob['clientJobId']) {
                    $('#logArea').val(clientJob['stdout'] ? clientJob['stdout'] : '');
                    $('#detailArea').val(JSON.stringify(clientJob, null, 2));
                    $('#killArea').val(clientJob['killLog'] ? clientJob['killLog'] : '');
                    $('.job-status').hide();
                    var status = clientJob['status'];
                    switch (status) {
                        case 'RUNNING' :
                            $('#status-running').show();
                            $('#status-kill').show();
                            break;
                        case 'FINISHED' :
                            $('#status-finished').show();
                            break;
                        case 'FAILED' :
                            $('#status-failed').show();
                            break;
                        case 'KILLED' :
                            $('#status-killed').show();
                            break;
                        case 'STOPPING' :
                            $('#status-stopping').show();
                            break;
                        case 'STANDBY' :
                            $('#status-standby').show();
                            break;
                        case 'KILL_FAIL' :
                            $('#status-kill-failed').show();
                            break;
                        default :
                            break;
                    }
                }
            }
        },
        /**
         * 폼의 디테일 부분을 초기화한다.
         */
        cleanFormDetail: function () {
            $('.job-status').hide();
            $('#logArea').val('');
            $('#detailArea').val('');
            $('#killArea').val('');
        },
        /**
         * 탭을 초기화하고, 클라이언트잡으로부터 데이터를 받아 폼을 재구성한다.
         * @param tab
         * @param clientJob
         */
        updateTabAsClientJob: function (tab, clientJob) {
            if (tab) {
                tab.removeData('collection');
                tab.data('clientJob', clientJob);
                var me = this;
                var name = clientJob['clientJobName'];
                var type = clientJob['clientJobType'];
                tab.find('a').html(name ? name : 'UnKnown');
                $('#jobTitle').html(name ? name : 'UnKnown');
                eval('me.setFormData(clientJob["' + type + 'Request"]);');
                me.makeActiveCollection();
                me.cleanFormDetail();
                me.updateFormDetail(clientJob);
            }
        },
        /**
         * 탭을 초기화하고, 콜렉션으로부터 데이터를 받아 폼을 재구성한다.
         * @param tab
         * @param data
         */
        updateTabAsCollection: function (tab, collectionData) {
            if (tab) {
                tab.removeData('clientJob');
                tab.data('collection', collectionData);
                var me = this;
                var type = collectionData.jobType;
                var name = collectionData.jobName;
                tab.find('a').html(name);
                $('#jobTitle').html(name);
                eval('me.setFormData(collectionData["' + type + 'Request"]);');
                me.makeActiveCollection();
                me.cleanFormDetail();
            }
        },
        /**
         * 탭의 이름만 변경하는 경우로,
         * 탭에서 Save As 를 통해 새로운 콜렉션으로 저장되었을 때와
         * 콜렉션 이름을 변경하였을 때 영향을 받는 탭에 변경된 이름을 적용하기 위해서이다.
         * 이때 탭에 저장된 객체에도 clientJobName 을 업데이트하여야 한다.
         *
         * @param tab
         * @param name
         */
        updateTabName: function (tab, name) {
            if (tab) {
                var me = this;
                tab.find('a').html(name);
                $('#jobTitle').html(name);
                $('#basic-params-form').find('input[name=clientJobName]').val(name);

                var tabData = tab.data('clientJob') ? tab.data('clientJob') : tab.data('collection');
                if (tabData) {
                    var data = JSON.parse(JSON.stringify(tabData));
                    eval('data["' + jobType + 'Request"]["clientJobName"] = name;');
                    if (tab.data('clientJob')) {
                        tab.data('clientJob', data);
                    } else {
                        data['jobName'] = name;
                        tab.data('collection', data);
                    }
                }
            }
        }
        ,
        getFormData: function () {
            var me = this;
            var parseFormData = function (form) {
                var formData = {};
                form.find('.form-group').each(function () {
                    var fieldData = $(this).data('data');
                    var fieldName = fieldData['name'];
                    var type = fieldData.type;
                    var value, checked;
                    if (type == 'textList') {
                        value = [];
                        $(this).find('.template-item').each(function () {
                            var itemVal = $(this).find('input').val();
                            if (!me.emptyString(itemVal)) {
                                value.push(itemVal);
                            }
                        });
                        if (value.length) {
                            formData[fieldName] = value;
                        }
                    } else if (type == 'textareaList') {
                        value = [];
                        $(this).find('.template-item').each(function () {
                            var itemVal = $(this).find('textarea').val();
                            if (!me.emptyString(itemVal)) {
                                value.push(itemVal);
                            }
                        });
                        if (value.length) {
                            formData[fieldName] = value;
                        }
                    } else if (type == 'map') {
                        value = {};
                        $(this).find('.template-item').each(function () {
                            var key = $(this).find('.keytab').val();
                            var keyValue = $(this).find('.valuetab').val();
                            if (!me.emptyString(key) && !me.emptyString(keyValue)) {
                                value[key] = keyValue;
                            }
                        });
                        if (!$.isEmptyObject(value)) {
                            formData[fieldName] = value;
                        }

                    } else if (type == 'text') {
                        value = $(this).find('[name=' + fieldName + ']').val();
                        if (!me.emptyString(value)) {
                            formData[fieldName] = value;
                        }
                    } else if (type == 'number') {
                        value = $(this).find('[name=' + fieldName + ']').val();
                        if (value) {
                            formData[fieldName] = value;
                        }
                    } else if (type == 'boolean') {
                        checked = $(this).find('[name=' + fieldName + ']').prop('checked');
                        if (checked) {
                            formData[fieldName] = true;
                        }
                    } else if (type == 'textarea') {
                        value = $(this).find('[name=' + fieldName + ']').val();
                        if (!me.emptyString(value)) {
                            formData[fieldName] = value;
                        }
                    }
                });
                return formData;
            };
            var basicParams = parseFormData($('#basic-params-form'));
            var nativeParams = parseFormData($('#native-params-form'));
            for (var attr in nativeParams) {
                basicParams[attr] = nativeParams[attr];
            }
            return basicParams;
        },
        setFormData: function (formData) {
            var me = this;
            var basicForm = $('#basic-params-form');
            var nativeForm = $('#native-params-form');
            basicForm.find('.form-group').remove();
            nativeForm.find('.form-group').remove();
            for (var i = 0; i < me.metadata.length; i++) {
                var data = me.metadata[i];
                if (data.basic) {
                    template.createField(basicForm, data, formData);
                } else {
                    template.createField(nativeForm, data, formData);
                }
            }
        }
        ,
        getConsoleHistory: function (cb) {
            var me = this;
            $.ajax({
                type: "GET",
                url: me.baseUrl + "/rest/v1/clientJob/consoleJob?jobType=" + jobType,
                async: me.async,
                crossDomain: true,
                data: '',
                dataType: "text",
                contentType: "application/json; charset=utf-8",
                success: function (response) {
                    cb(JSON.parse(response));
                },
                error: function (request, status, errorThrown) {
                    cb(null, errorThrown);
                }
            });
        }
        ,
        getCollections: function (cb) {
            var me = this;
            $.ajax({
                type: "GET",
                url: me.baseUrl + "/rest/v1/collection?jobType=" + jobType,
                async: me.async,
                crossDomain: true,
                data: '',
                dataType: "text",
                contentType: "application/json; charset=utf-8",
                success: function (response) {
                    cb(JSON.parse(response));
                },
                error: function (request, status, errorThrown) {
                    cb(null, errorThrown);
                }
            });
        }
        ,
        getRequestDesc: function () {
            var me = this;
            var result = null;
            $.ajax({
                type: "GET",
                url: me.baseUrl + "/eco/clientJob/requestDesc?jobType=" + jobType,
                crossDomain: true,
                async: false,
                data: '',
                dataType: "text",
                contentType: "application/json; charset=utf-8",
                success: function (response) {
                    result = JSON.parse(response);
                },
                error: function (request, status, errorThrown) {

                }
            });
            return result;
        }
        ,
        drawFields: function () {
            var me = this;
            var requestDesc = me.getRequestDesc();
            if (requestDesc) {
                me.metadata = requestDesc;
                for (var i = 0; i < me.metadata.length; i++) {
                    var data = me.metadata[i];
                    if (data.basic) {
                        template.createField($('#basic-params-form'), data);
                    } else {
                        template.createField($('#native-params-form'), data);
                    }
                }
            }
        }
        ,
        drawHistories: function () {
            var me = this;
            me.getConsoleHistory(function (data, err) {
                if (!data) {
                    return;
                }
                //히스토리가 없을경우 탭하나를 생성한다.
                var tab;
                if (!data.length) {
                    tab = me.createHistoryTab({});
                    tab.find('a').click();
                } else {
                    for (var i = data.length - 1; i >= 0; i--) {
                        tab = me.createHistoryTab(data[i]);
                        if (i == 0) {
                            tab.find('a').click();
                        }
                    }
                }
            })
        }
        ,
        /**
         * 탭을 추가하거나 업데이트하고, 탭 클릭시 clientJob 데이터를 기반으로 필드값을 구성한다.
         * @param clientJob
         * @param existTab
         * @returns {*}
         */
        createHistoryTab: function (clientJob, existTab) {
            var me = this;
            var tab;
            if (existTab) {
                tab = existTab;
            } else {
                /**
                 * 신규 탭을 추가하기 전에 기존 탭의 개수를 파악하고 maxTab 을 넘는다면 첫번째 요소를 삭제한다.
                 * @type {number|jQuery}
                 */
                var tabCount = $('.history-tab').length;
                if (tabCount >= 7) {
                    $('.history-tab').first().remove();
                }
                tab = $('<li class="history-tab">' +
                    '<a style="width: 80px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;display: block;" href="#editorPanel" data-toggle="tab" aria-expanded="false"></a>' +
                    '<div name="closeBtn" style="position:absolute;top:-15px;right:0%"><i class="fa fa-times-circle"></i></div>' +
                    '</li>');
                tab.insertBefore($('#history-tab-plus'));
                tab.find('[name=closeBtn]').click(function (event) {
                    event.stopPropagation();
                    var index = tab.index();
                    var alltabs = me.getAlltabs();
                    var nextTab;
                    var nextIndex;
                    if (index == 0) {
                        nextIndex = index + 1;
                    } else if (index > 0) {
                        nextIndex = index - 1;
                    }
                    alltabs.each(function () {
                        if ($(this).index() == nextIndex) {
                            nextTab = $(this);
                        }
                    });
                    console.log(nextIndex);
                    if (nextTab) {
                        nextTab.click();
                    }
                    tab.remove();
                })
            }
            /**
             * 데이터 바인딩 과정:
             * 탭을 클릭시에 active 클래스의 반영이 css 에 의해 느리게 처리됨으로, 즉시 active 데이터를 반영한다.
             * clientJob 데이터가 매핑되는 경우와, 콜렉션 데이터가 매핑되는 경우 두가지가 있다.
             */
            tab.removeData('collection');
            tab.data('clientJob', clientJob);
            tab.find('a').html(clientJob['clientJobName'] ? clientJob['clientJobName'] : 'UnKnown');
            tab.click(function () {
                me.getAlltabs().each(function () {
                    $(this).data('active', false);
                    $(this).removeClass('active');
                    $(this).find('[name=closeBtn]').hide();
                });
                tab.data('active', true);
                $(this).addClass('active');
                tab.find('[name=closeBtn]').show();
                var collectionData = tab.data('collection');
                var clientJobData = tab.data('clientJob');
                if (collectionData) {
                    me.updateTabAsCollection(tab, collectionData);
                } else if (clientJobData) {
                    me.updateTabAsClientJob(tab, clientJobData);
                }
            });

            return tab;
        },
        /**
         * 화면에 존재하는 콜렌션 엘리먼트 리스트를 반환한다.
         * @returns {*|jQuery|HTMLElement}
         */
        getCollectionItems: function () {
            return $('.collection');
        },
        /**
         * 주어진 이름에 해당하는 콜렉션을 반환한다.
         * @param name
         * @returns {*}
         */
        selectCollectionByName: function (name) {
            var me = this;
            var match;
            var items = me.getCollectionItems();
            items.each(function () {
                var data = $(this).data('data');
                if (data) {
                    if (data['jobName'] == name) {
                        match = $(this);
                    }
                }
            });
            return match;
        }
        ,
        /**
         * 콜렉션 리스트를 서버로부터 받아와 화면에 재구성한다.
         * 콜렉션을 클릭했을 경우 현재 활성화 된 탭을 콜렉션의 정보로 오버라이드한다.
         */
        drawCollections: function () {
            var me = this;
            me.getCollectionItems().remove();
            me.getCollections(function (collections, err) {
                if (!collections) {
                    return;
                }
                for (var i = 0; i < collections.length; i++) {
                    var collection = template.createCollection($('#collection-list'), collections[i]);
                    collection.data('data', collections[i]);
                    collection.click(function () {
                        var data = $(this).data('data');
                        me.updateTabAsCollection(me.getActiveTab(), data);

                        //자기자신을 제외한 다른 콜렉션 메뉴를 닫는다.
                        me.getCollectionItems().each(function () {
                            if ($(this).data('data')['_id'] !== data['_id']) {
                                $(this).find('.coll-menu').hide();
                            }
                        });
                    });
                    me.bindCollectionMenuEvent(collection, collections[i]);
                }
                me.makeActiveCollection();
            })
        },
        bindCollectionMenuEvent: function (collection) {
            var me = this;
            var editBtn = collection.find('.coll-edit');
            var copyBtn = collection.find('.coll-copy');
            var delBtn = collection.find('.coll-del');
            editBtn.unbind('click');
            copyBtn.unbind('click');
            delBtn.unbind('click');
            editBtn.click(function () {
                collection.find('.coll-menu').hide();
                /**
                 * JSON 을 사용하여 데이터 무결성 처리를 하지 않을 경우, 업데이트가 실패하였을 경우에도 엘리먼트의 데이터에 영향을 끼친다.
                 */
                var data = JSON.parse(JSON.stringify(collection.data('data')));
                var originalName = data.jobName;
                var modal = $('#renameModal');
                modal.find('[name=name]').val(originalName);
                modal.find('[name=action]').unbind('click');
                modal.find('[name=action]').click(function () {
                    modal.find('.close').click();
                    var name = modal.find('[name=name]').val().trim();
                    if (name.length < 1) {
                        return;
                    }
                    data['jobName'] = name;
                    eval('data["' + jobType + 'Request"]["clientJobName"] = name;');
                    blockStart();
                    $.ajax({
                        type: "PUT",
                        url: "/rest/v1/collection/" + data['_id'],
                        data: JSON.stringify(data),
                        dataType: "text",
                        contentType: "application/json; charset=utf-8",
                        success: function (response) {
                            blockStop();
                            var tabByName = me.selectTabByName(originalName);
                            for (var i = 0; i < tabByName.length; i++) {
                                me.updateTabName(tabByName[i], name);
                            }
                            me.drawCollections();
                        },
                        error: function (request, status, errorThrown) {
                            if (request.status === 409) {
                                msgBox(name + ' is already exist.');
                            } else {
                                msgBox('Failed to update collection.');
                            }
                            blockStop();
                        }
                    });
                });
                modal.modal({
                    show: true
                });
            });
            copyBtn.click(function () {
                collection.find('.coll-menu').hide();
                /**
                 * JSON 을 사용하여 데이터 무결성 처리를 하지 않을 경우, 업데이트가 실패하였을 경우에도 엘리먼트의 데이터에 영향을 끼친다.
                 */
                var data = JSON.parse(JSON.stringify(collection.data('data')));
                var originalName = data.jobName;
                var rename = originalName + ' copy';
                data['jobName'] = rename;
                eval('data["' + jobType + 'Request"]["clientJobName"] = rename;');
                delete data['_id'];
                delete data['_rev'];
                blockStart();
                $.ajax({
                    type: "POST",
                    url: "/rest/v1/collection",
                    data: JSON.stringify(data),
                    dataType: "text",
                    contentType: "application/json; charset=utf-8",
                    success: function (response) {
                        blockStop();
                        me.drawCollections();
                    },
                    error: function (request, status, errorThrown) {
                        msgBox('Failed to copy collection.');
                        blockStop();
                    }
                });
            });
            delBtn.click(function () {
                collection.find('.coll-menu').hide();
                /**
                 * JSON 을 사용하여 데이터 무결성 처리를 하지 않을 경우, 업데이트가 실패하였을 경우에도 엘리먼트의 데이터에 영향을 끼친다.
                 */
                var data = JSON.parse(JSON.stringify(collection.data('data')));
                blockStart();
                $.ajax({
                    type: "DELETE",
                    url: "/rest/v1/collection/" + data['_id'],
                    data: '',
                    dataType: "text",
                    contentType: "application/json; charset=utf-8",
                    success: function (response) {
                        blockStop();
                        me.drawCollections();
                    },
                    error: function (request, status, errorThrown) {
                        msgBox('Failed to delete collection.');
                        blockStop();
                    }
                });
            });
        },
        /**
         * 액티브탭의 잡네임을 찾아와 매치되는 콜렉션의 클래스에 액티브를 추가한다.
         * @returns {*}
         */
        makeActiveCollection: function () {
            var match;
            var me = this;
            var activeTab = me.getActiveTab();
            if (activeTab) {
                var name = activeTab.find('a').html();
                var items = me.getCollectionItems();
                items.removeClass('active');
                items.each(function () {
                    var data = $(this).data('data');
                    if (data) {
                        if (data['jobName'] == name) {
                            match = $(this);
                            match.addClass('active');
                        }
                    }
                });
            }
            return match;
        }
    };

    controller.init();
});