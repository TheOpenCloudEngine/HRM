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
            } else if (type == 'map') {
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
        },
        createComplexField: function (element, type) {
            var me = this;
            var field = $(me.complexTemplate());
            field.find('.addBtn').click(function () {
                me.newComplexItem(field, type);
            });
            element.append(field);
            me.newComplexItem(field, type);
            return field;
        },
        createTextField: function (element) {
            var me = this;
            var field = $(me.textTemplate());
            element.append(field);
            return field;
        },
        createTextAreaField: function (element) {
            var me = this;
            var field = $(me.textAreaTemplate());
            element.append(field);
            return field;
        },
        createNumberField: function (element) {
            var me = this;
            var field = $(me.numberTemplate());
            element.append(field);
            return field;
        },
        createBooleanField: function (element) {
            var me = this;
            var field = $(me.booleanTemplate());
            element.append(field);
            return field;
        },
        createField: function (element, data) {
            var me = this;
            var name = data.name;
            var type = data.type;
            var description = data.description;
            var field;
            if (type == 'textList' || type == 'map') {
                field = me.createComplexField(element, type);
                field.data('data', data);
            } else if (type == 'text') {
                field = me.createTextField(element);
                field.data('data', data);
            } else if (type == 'number') {
                field = me.createNumberField(element);
                field.data('data', data);
            } else if (type == 'boolean') {
                field = me.createBooleanField(element);
                field.data('data', data);
            } else if (type == 'textarea') {
                field = me.createTextAreaField(element);
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
        }
    };

    var controller = {
        baseUrl: 'http://52.78.88.87:8080',
        maxTab: 7,
        async: true,
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
        }
        ,
        send: function () {
            this.getFormData();
        }
        ,
        saveAs: function () {
            var data = {
                jobType: jobType
            };
            eval('data["'+jobType+'Request"] = this.getFormData();');
            console.log(data);
            //var modal = $('#saveAsModal');
            //modal.find('[name=action]').unbind('click');
            //modal.find('[name=action]').click(function () {
            //    modal.find('.close').click();
            //    var name = modal.find('[name=name]').val().trim();
            //    if (name.length < 1) {
            //        return;
            //    }
            //    blockStart();
            //    $.ajax({
            //        type: "PUT",
            //        url: "/rest/v1/hdfs/rename?path=" + path + '&rename=' + name,
            //        data: '',
            //        dataType: "text",
            //        contentType: "application/json; charset=utf-8",
            //        success: function (response) {
            //
            //            blockStop();
            //        },
            //        error: function (request, status, errorThrown) {
            //
            //            blockStop();
            //        }
            //    });
            //});
            //modal.modal({
            //    show: true
            //});
            //this.getMatchingCollection(data.clientJobName);

        },
        getMatchingCollection: function (clientJobName) {
            return null;
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
        }
        ,
        getConsoleHistory: function (cb) {
            var me = this;
            $.ajax({
                type: "GET",
                url: me.baseUrl + "/rest/v1/clientJob/consoleJob?jobType=" + jobType,
                async: me.async,
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
                for (var i = 0; i < requestDesc.length; i++) {
                    var data = requestDesc[i];
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
                    $.each(data, function (index, clientJob) {
                        tab = me.createHistoryTab(clientJob);
                        if (index == data.length - 1) {
                            tab.find('a').click();
                        }
                    })
                }
            })
        }
        ,
        //탭을 추가하거나 업데이트하고, 탭 클릭시 clientJob 데이터를 기반으로 필드값을 구성한다.
        createHistoryTab: function (clientJob, existTab) {
            var me = this;
            var tab;
            if (existTab) {
                tab = existTab;
            } else {
                //신규 탭을 추가하기 전에 기존 탭의 개수를 파악하고 maxTab 을 넘는다면 첫번째 요소를 삭제한다.
                var tabCount = $('.history-tab').length;
                if (tabCount >= 7) {
                    $('.history-tab').first().remove();
                }
                tab = $('<li class="history-tab"><a style="width: 80px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;display: block;" href="#editorPanel" data-toggle="tab" aria-expanded="false">Show table1</a></li>');
                tab.insertBefore($('#history-tab-plus'));
            }
            //TODO 데이터 바인딩 과정
            tab.find('a').html(clientJob['clientJobName'] ? clientJob['clientJobName'] : 'UnKnown');
            tab.click(function () {

            });

            return tab;
        }
        ,
        drawCollections: function () {

        }
    };

    controller.init();


    //getConsoleHistory(function(){
    //
    //});
});