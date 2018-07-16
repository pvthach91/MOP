﻿(function ($) {
    if (/^1.2/.test($.fn.jquery) || /^1.1/.test($.fn.jquery)) {
        alert("requires jQuery v1.3 or later!  You are using v" + $.fn.jquery);
        return
    }
    $(".wfts .p01").hover(function () {
            $(".wfts-text").show()
        },
        function () {
            $(".wfts-text").hide()
        });
    $.alert = function (msg, title, successMethod) {
        layer.open({
            icon: 0,
            title: title || "温馨提示",
            content: msg,
            yes: function () {
                layer.closeAll();
                successMethod && successMethod()
            }
        })
    };
    $.confirm = function (msg, successMethod, failMethod) {
        layer.open({
            content: msg,
            btn: ["确定", "取消"],
            yes: function () {
                layer.closeAll();
                successMethod && successMethod()
            },
            cancel: function () {
                layer.closeAll();
                failMethod && failMethod()
            }
        })
    };
    $(".reveal-modal-bg").on("click",
        function () {
            this.parentNode.style.display = "none"
        });
    $(document).ready(function () {
        $.playInit({
            data_label: face,
            data_prize: pri_user_data,
            lotteryid: pri_lotteryid,
            isdynamic: pri_isdynamic,
            ajaxurl: pri_ajaxurl
        })
    });
    $.playInit = function (opts) {
        var ps = {
            data_label: [],
            data_prize: [],
            data_id: {
                id_labelbox: "#tabbar-div-s2",
                id_smalllabel: "#tabbar-div-s3",
                id_desc: "#lt_desc",
                id_help: "#lt_help",
                id_example: "#lt_example",
                id_selector: "#lt_selector",
                id_sel_num: "#lt_sel_nums",
                id_sel_money: "#lt_sel_money",
                id_sel_times: "#lt_sel_times",
                id_reduce_times: "#reducetime",
                id_plus_times: "#plustime",
                id_sel_insert: "#lt_sel_insert",
                id_sel_modes: "#lt_sel_modes",
                id_sel_prize: "#lt_sel_prize",
                id_cf_count: "#lt_cf_count",
                id_cf_clear: "#lt_cf_clear",
                id_cf_content: "#lt_cf_content",
                id_cf_num: "#lt_cf_nums",
                id_cf_money: "#lt_cf_money",
                id_issues: "#lt_issues",
                id_fastok: "#lt_fast_buy",
                id_sendok: "#lt_buy",
                id_tra_if: "#lt_trace_if",
                id_tra_stop: "#lt_trace_stop",
                id_tra_box: "#lt_trace_box",
                id_tra_alct: "#lt_trace_alcount",
                id_tra_label: "#lt_trace_label",
                id_tra_lhtml: "#lt_trace_labelhtml",
                id_tra_ok: "#lt_trace_ok",
                id_tra_issues: "#lt_trace_issues",
                lt_tra_count: "#lt_issue_start"
            },
            ajaxurl: "",
            lotteryid: 1,
            isdynamic: 1,
            ontimeout: function () {
            },
            onfinishbuy: function () {
            },
            test: ""
        };
        opts = $.extend({},
            ps, opts || {});
        $.extend({
            lt_id_data: opts.data_id,
            lt_method_data: {},
            lt_method: methods,
            lt_issues: opts.issues,
            lt_ajaxurl: opts.ajaxurl,
            lt_lottid: opts.lotteryid,
            lt_isdyna: opts.isdynamic,
            lt_total_nums: 0,
            lt_total_money: 0,
            lt_time_leave: 0,
            lt_time_open: 0,
            lt_open_status: true,
            lt_same_code: [],
            lt_ontimeout: opts.ontimeout,
            lt_onfinishbuy: opts.onfinishbuy,
            lt_trace_base: 0,
            lt_submiting: false,
            lt_ismargin: true,
            lt_prizes: [],
            lt_rxmode: false,
            lt_position_sel: [],
            lt_position_title: lot_lang.dec_s41,
            lt_total_money_trace: 0
        });
        ps = null;
        opts.data_id = null;
        opts.issues = null;
        opts.ajaxurl = null;
        opts.lotteryid = null;
        if ($.browser.msie) {
            CollectGarbage()
        }
        var noRightMethod = [];
        var noRightGroup = [];
        var haveRight = false;
        $.each(opts.data_label,
            function (l, h) {
                noRightGroup = [];
                $.each(h.label,
                    function (i, n) {
                        noRightMethod = [];
                        $.each(n.label,
                            function (j, m) {
                                haveRight = false;
                                for (k in opts.data_prize) {
                                    if (m.methodid == opts.data_prize[k].methodid) {
                                        m.prize = opts.data_prize[k].prize;
                                        m.dyprize = opts.data_prize[k].dyprize;
                                        haveRight = true;
                                        break
                                    }
                                }
                                if (haveRight == false) {
                                    noRightMethod.push(m)
                                }
                            });
                        for (var ll = 0; ll < noRightMethod.length; ll++) {
                            opts.data_label[l].label[i].label.remove(noRightMethod[ll])
                        }
                        if (opts.data_label[l].label[i].label.length == 0) {
                            noRightGroup.push(n)
                        }
                    });
                for (var mm = 0; mm < noRightGroup.length; mm++) {
                    opts.data_label[l].label.remove(noRightGroup[mm])
                }
            });
        var bhtml = "";
        var postion = 0;
        var haverx = 0;
        $.each(opts.data_label,
            function (i, n) {
                if (n.label.length > 0) {
                    if (typeof(n) == "object") {
                        if (n.isrx == 1 && haverx == 0) {
                            haverx = 1
                        }
                        if (postion == 0 || n.isdefault == 1) {
                            bhtml = bhtml.replace('class="on"', "");
                            bhtml += '<a class="on" value="' + i + '" tag="' + n.isrx + '" default="' + n.isdefault + '" href="javascript:void(0)">' + n.title + "</a>";
                            lt_smalllabel({
                                title: n.title,
                                label: n.label
                            })
                        } else {
                            bhtml += '<a value="' + i + '" tag="' + n.isrx + '" default="' + n.isdefault + '" href="javascript:void(0)">' + n.title + "</a>"
                        }
                    }
                    postion++
                }
            });
        if (haverx == 1) {
            var rhtml = [];
            rhtml.push('<a class="false on" href="javascript:void(0)">' + lot_lang.dec_s42 + "</a>");
            rhtml.push('<a class="true" href="javascript:void(0)">' + lot_lang.dec_s43 + "</a>");
            $("#tabbar-div-s2-right").html(rhtml.join(""))
        }
        $bhtml = $(bhtml);
        $($.lt_id_data.id_labelbox).empty();
        $bhtml.appendTo($.lt_id_data.id_labelbox);
        $.each($($.lt_id_data.id_labelbox).children(),
            function (i, n) {
                if ($.lt_rxmode == false) {
                    if ($(this).attr("tag") == 1) {
                        $(this).hide()
                    }
                } else {
                    if ($(this).attr("tag") == 0) {
                        $(this).hide()
                    }
                }
            });
        $("#tabbar-div-s2-right a").click(function () {
            var $this = $(this);
            if ($this.hasClass("on")) {
                return false
            }
            $this.addClass("on").siblings().removeClass("on");
            if ($this.hasClass("true")) {
                $.lt_rxmode = true
            } else {
                $.lt_rxmode = false
            }
            var j = 0;
            $.each($($.lt_id_data.id_labelbox).children(),
                function (i, n) {
                    if ($.lt_rxmode == false) {
                        if ($(this).attr("tag") == 1) {
                            $(this).hide()
                        } else {
                            $(this).show();
                            if (j == 0 || $(this).attr("default") == 1) {
                                $(this).click()
                            }
                            j++
                        }
                    } else {
                        if ($(this).attr("tag") == 0) {
                            $(this).hide()
                        } else {
                            $(this).show();
                            if (j == 0 || $(this).attr("default") == 1) {
                                $(this).click()
                            }
                            j++
                        }
                    }
                })
        });
        $($.lt_id_data.id_labelbox).children().click(function () {
            if ($.trim($(this).attr("class")) == "on" || $.trim($(this).attr("id")) == "changemode") {
                return
            }
            $(this).attr("class", "on").siblings(".on").removeClass("on");
            var index = parseInt($(this).attr("value"), 10);
            lt_smalllabel({
                title: opts.data_label[index].title,
                label: opts.data_label[index].label
            })
        });
        $("li.item", $($.lt_id_data.id_cf_content)).live("mouseover",
            function () {
                $(this).addClass("on")
            }).live("mouseout",
            function () {
                $(this).removeClass("on")
            });
        $($.lt_id_data.id_cf_clear).click(function () {
            $.confirm(lot_lang.am_s5,
                function () {
                    $.lt_total_nums = 0;
                    $.lt_total_money = 0;
                    $.lt_trace_base = 0;
                    $.lt_same_code = [];
                    $($.lt_id_data.id_cf_num).html(0);
                    $($.lt_id_data.id_cf_money).html(0);
                    $($.lt_id_data.id_cf_count).html(0);
                    $($.lt_id_data.id_cf_content).children().filter(".item").remove();
                    $('<li class="item none">暂无投注项</li>').appendTo($.lt_id_data.id_cf_content);
                    cleanTraceIssue();
                    if ($.lt_ismargin == false) {
                        traceCheckMarginSup()
                    }
                },
                function () {
                    cleanTraceIssue()
                })
        });
        $($.lt_id_data.id_help).mouseover(function () {
            var $h = $('<div class="tip_examplehelp">' + $.lt_method_data.methodhelp + "</div>");
            var offset = $(this).offset();
            var left = offset.left - 37;
            var top = offset.top + 10;
            $(this).openFloat($h, "more", left, top)
        }).mouseout(function () {
            $(this).closeFloat()
        });
        $($.lt_id_data.id_example).mouseover(function () {
            var $h = $('<div class="tip_examplehelp">' + $.lt_method_data.methodexample + "</div>");
            var offset = $(this).offset();
            var left = offset.left;
            var top = offset.top + 40;
            $(this).openFloat($h, "more", left, top)
        }).mouseout(function () {
            $(this).closeFloat()
        });
        $($.lt_id_data.id_sendok).alertSubmit();
        $($.lt_id_data.id_fastok).fastSubmit();
        $.fn.updateWinnumber()
    };
    var lt_smalllabel = function (opts) {
        var ps = {
            title: "",
            label: []
        };
        opts = $.extend({},
            ps, opts || {});
        var html = "";
        var dyhtml = "";
        var hasmore = 0;
        $.each(opts.label,
            function (j, m) {
                if (m.label.length > 0) {
                    html += "<dl>";
                    if (!(opts.label.length == 1 && m.label.length == 1)) {
                        hasmore = 1;
                        html += '<dt class="color01">' + m.gtitle + "</dt>"
                    }
                    $.each(m.label,
                        function (i, n) {
                            if (typeof(n) == "object") {
                                if (j == 0 && i == 0) {
                                    if (!(opts.label.length == 1 && m.label.length == 1)) {
                                        html += '<dd class="on"><a id="smalllabel_' + j + "_" + i + '" href="javascript:void(0)">' + n.desc + "</a></dd>"
                                    }
                                    lt_selcountback();
                                    $.lt_method_data = {
                                        methodid: n.methodid,
                                        title: opts.title,
                                        name: n.name,
                                        str: n.show_str,
                                        prize: n.prize,
                                        dyprize: n.dyprize,
                                        modes: $.lt_method_data.modes ? $.lt_method_data.modes : {},
                                        sp: n.code_sp,
                                        methodhelp: n.methodhelp,
                                        methoddesc: n.methoddesc,
                                        methodexample: n.methodexample,
                                        maxcodecount: n.maxcodecount,
                                        isrx: n.isrx,
                                        numcount: n.numcount,
                                        defaultposition: n.defaultposition
                                    };
                                    $($.lt_id_data.id_selector).lt_selectarea(n.selectarea);
                                    selmodes = getCookie("modes");
                                    $($.lt_id_data.id_sel_modes).empty();
                                    $.each(n.modes,
                                        function (j, m) {
                                            $.lt_method_data.modes[m.modeid] = {
                                                name: m.name,
                                                rate: Number(m.rate)
                                            };
                                            if (selmodes == undefined) {
                                                selmodes = m.modeid
                                            }
                                            var span = document.createElement("span");
                                            span.value = span.className = m.modeid;
                                            span.innerHTML = m.name;
                                            $($.lt_id_data.id_sel_modes).append(span)
                                        });
                                    $($.lt_id_data.id_sel_modes).attr("value", selmodes).find("." + selmodes).addClass("on");
                                    $($.lt_id_data.id_sel_modes).children().click(function () {
                                        $(this).addClass("on").siblings(".on").removeClass("on").parent().attr("value", this.value);
                                        id_sel_modes_change()
                                    });
                                    dypoint = getCookie("dypoint");
                                    $($.lt_id_data.id_sel_prize).empty();
                                    if (n.dyprize.length == 1 && $.lt_isdyna == 1) {
                                        dyhtml = '<SELECT name="lt_sel_dyprize" id="lt_sel_dyprize"  style="cursor:pointer">';
                                        $.each(n.dyprize[0].prize,
                                            function (j, m) {
                                                dyhtml += '<OPTION value="' + m.prize + "|" + m.point + '"' + (dypoint == m.point ? " selected" : "") + ">" + m.prize + "-" + (Math.ceil(m.point * 1000) / 10) + "%</OPTION>"
                                            });
                                        dyhtml += "</SELECT>";
                                        $($.lt_id_data.id_sel_prize).html("");
                                        $(dyhtml).appendTo($.lt_id_data.id_sel_prize)
                                    }
                                } else {
                                    if (!(opts.label.length == 1 && m.label.length == 1)) {
                                        html += '<dd><a id="smalllabel_' + j + "_" + i + '" href="javascript:void(0)">' + n.desc + "</a></dd>"
                                    }
                                }
                            }
                        });
                    html += "</dl>"
                }
            });
        $html = $(html);
        $($.lt_id_data.id_smalllabel).empty();
        $html.appendTo($.lt_id_data.id_smalllabel);
        if (hasmore == 0) {
            $($.lt_id_data.id_smalllabel).empty();
            $($.lt_id_data.id_smalllabel).parent().hide()
        } else {
            $($.lt_id_data.id_smalllabel).parent().show()
        }
        $("[id^='smalllabel_']:first", $($.lt_id_data.id_smalllabel)).attr("class", "on").data("ischecked", "yes");
        $("[id^='smalllabel_']", $($.lt_id_data.id_smalllabel)).click(function () {
            if ($(this).data("ischecked") == "yes") {
                return
            }
            var aIdIndex = $(this).attr("id").split("_");
            var groupindex = parseInt(aIdIndex[1], 10);
            var index = parseInt(aIdIndex[2], 10);
            var tmpopts = opts.label;
            tmpopts = tmpopts[groupindex];
            lt_selcountback();
            $.lt_method_data = {
                methodid: tmpopts.label[index].methodid,
                title: tmpopts.gtitle,
                name: tmpopts.label[index].name,
                str: tmpopts.label[index].show_str,
                prize: tmpopts.label[index].prize,
                dyprize: tmpopts.label[index].dyprize,
                modes: $.lt_method_data.modes ? $.lt_method_data.modes : {},
                sp: tmpopts.label[index].code_sp,
                methoddesc: tmpopts.label[index].methoddesc,
                methodhelp: tmpopts.label[index].methodhelp,
                methodexample: tmpopts.label[index].methodexample,
                maxcodecount: tmpopts.label[index].maxcodecount,
                isrx: tmpopts.label[index].isrx,
                numcount: tmpopts.label[index].numcount,
                defaultposition: tmpopts.label[index].defaultposition
            };
            $("[id^='smalllabel_']", $($.lt_id_data.id_smalllabel)).removeData("ischecked").parent().attr("class", "");
            $(this).data("ischecked", "yes").parent().attr("class", "on");
            $($.lt_id_data.id_selector).lt_selectarea(tmpopts.label[index].selectarea);
            $($.lt_id_data.id_sel_modes).empty();
            selmodes = getCookie("modes");
            $.each(tmpopts.label[index].modes,
                function (j, m) {
                    $.lt_method_data.modes[m.modeid] = {
                        name: m.name,
                        rate: Number(m.rate)
                    };
                    if (selmodes == undefined) {
                        selmodes = m.modeid
                    }
                    var span = document.createElement("span");
                    span.value = span.className = m.modeid;
                    span.innerHTML = m.name;
                    $($.lt_id_data.id_sel_modes).append(span)
                });
            $($.lt_id_data.id_sel_modes).attr("value", selmodes).find("." + selmodes).addClass("on");
            $($.lt_id_data.id_sel_modes).children().click(function () {
                $(this).addClass("on").siblings(".on").removeClass("on").parent().attr("value", this.value);
                id_sel_modes_change()
            });
            dypoint = getCookie("dypoint");
            $($.lt_id_data.id_sel_prize).empty();
            if (tmpopts.label[index].dyprize.length == 1 && $.lt_isdyna == 1) {
                dyhtml = '<SELECT name="lt_sel_dyprize" id="lt_sel_dyprize"  style="cursor:pointer">';
                $.each(tmpopts.label[index].dyprize[0].prize,
                    function (j, m) {
                        dyhtml += '<OPTION value="' + m.prize + "|" + m.point + '"' + (dypoint == m.point ? " selected" : "") + ">" + m.prize + "-" + (Math.ceil(m.point * 1000) / 10) + "%</OPTION>"
                    });
                dyhtml += "</SELECT>";
                $($.lt_id_data.id_sel_prize).html("");
                $(dyhtml).appendTo($.lt_id_data.id_sel_prize)
            }
        })
    };
    var id_sel_modes_change = function () {
        var nums = parseInt($($.lt_id_data.id_sel_num).html(), 10);
        var times = parseInt($($.lt_id_data.id_sel_times).val(), 10);
        var modes = parseInt($($.lt_id_data.id_sel_modes).attr("value"), 10);
        var money = Math.round(times * nums * 2 * ($.lt_method_data.modes[modes].rate * 1000)) / 1000;
        money = isNaN(money) ? 0 : money;
        $($.lt_id_data.id_sel_money).html(money)
    };
    var lt_selcountback = function () {
        $($.lt_id_data.id_sel_times).val(1);
        $($.lt_id_data.id_sel_money).html(0);
        $($.lt_id_data.id_sel_num).html(0)
    };
    $.fn.lt_selectarea = function (opts) {
        var ps = {
            type: "digital",
            layout: [{
                title: "\u767e\u4f4d",
                no: "0|1|2|3|4|5|6|7|8|9",
                place: 0,
                cols: 1
            },
                {
                    title: "\u5341\u4f4d",
                    no: "0|1|2|3|4|5|6|7|8|9",
                    place: 1,
                    cols: 1
                },
                {
                    title: "\u4e2a\u4f4d",
                    no: "0|1|2|3|4|5|6|7|8|9",
                    place: 2,
                    cols: 1
                }],
            noBigIndex: 5,
            isButton: true
        };
        opts = $.extend({},
            ps, opts || {});
        var data_sel = [];
        var minchosen = [];
        var max_place = 0;
        var otype = opts.type.toLowerCase();
        var methodname = $.lt_method[$.lt_method_data.methodid];
        var defaultposition = $.lt_method_data.defaultposition;
        var html = "";
        var positionvalue = 0;
        $.lt_position_sel = [];
        if (opts.selPosition == true) {
            defaultposition = defaultposition.split("");
            html += '<div class="selposition">';
            var positionlen = defaultposition.length;
            for (var i = 0; i < positionlen; i++) {
                if (defaultposition[i] == 1) {
                    $.lt_position_sel.push(i);
                    html += '<label for="position_' + i + '"><input type="checkbox" name="position_' + i + '" id="position_' + i + '" value="1" class="selpositioninput" checked>' + $.lt_position_title[i] + "</label>"
                } else {
                    html += '<label for="position_' + i + '"><input type="checkbox" name="position_' + i + '" id="position_' + i + '" value="1" class="selpositioninput">' + $.lt_position_title[i] + "</label>"
                }
            }
            html += lot_lang.dec_s45.replace("%n", $.lt_method_data.numcount) + "</div>"
        }
        if (otype == "input") {
            var tempdes = lot_lang.dec_s4;
            html += '<div class="nbs single"><table class=ha><tr><td valign=top><textarea id="lt_write_box" class="danshi-box ml20 mt10 f-left"></textarea><br />' + tempdes + "</td>" + "<td valign=top>" + "<div>" + '<span class="inline wrap-style ml20 mt30">' + '<input id="lt_write_del" name="lt_write_del" class="inline wrap-style-insaid pointer" type="button" value="删除重复号码">' + "</span>" + "</div>" + "<div>" + '<span class="inline wrap-style ml20 mt10">' + '<input id="lt_write_import" name="lt_write_import" class="inline wrap-style-insaid pointer" type="button" value="导入文件">' + "</span>" + "</div>" + "<div>" + '<span class="inline wrap-style ml20 mt10">' + '<input id="lt_write_empty" name="lt_write_empty" class="inline wrap-style-insaid pointer" type="button" value="&nbsp;\u6e05&nbsp;&nbsp;\u7a7a&nbsp;">' + "</span>" + "</div>" + "</td></tr></table></div>";
            data_sel[0] = [];
            tempdes = null
        } else {
            if (otype == "digital" || otype == "digitalts") {
                $.each(opts.layout,
                    function (i, n) {
                        if (typeof(n) == "object") {
                            n.place = parseInt(n.place, 10);
                            max_place = n.place > max_place ? n.place : max_place;
                            data_sel[n.place] = [];
                            minchosen[n.place] = (typeof(n.minchosen) == "undefined") ? 0 : n.minchosen;
                            html += "<li>";
                            if (n.cols > 0) {
                                html += '<div class="li_l color01">';
                                if (n.title.length > 0) {
                                    html += n.title
                                }
                                html += "</div>"
                            } else {
                                html += '<div class="li_l color01"></div>'
                            }
                            if (otype == "digital") {
                                html += '<div class="li_m">'
                            } else {
                                html += '<div class="li_m">'
                            }
                            numbers = n.no.split("|");
                            j = numbers.length;
                            for (i = 0; i < j; i++) {
                                if ((methodname == "ZXHZ" && i == 14) || (methodname == "ZUHZ" && i == 13) || (methodname == "ZXHZ2" && i == 10)) {
                                    html += "</span><span>"
                                }
                                /**
                                 * peter
                                 * 如果文字多了用方块
                                 **/
                                if (numbers[i].length > 1) {
                                    html += '<div name="lt_place_' + n.place + '">' + numbers[i] + "</div>"
                                } else {
                                    html += '<span name="lt_place_' + n.place + '">' + numbers[i] + "</span>"
                                }
                            }
                            html += "</div>";
                            if (opts.isButton == true) {
                                html += '<div class="li_r"><span class="dxjoq" name="all">' + lot_lang.bt_sel_all + '</span><span class="dxjoq" name="big">' + lot_lang.bt_sel_big + '</span><span class="dxjoq" name="small">' + lot_lang.bt_sel_small + '</span><span class="dxjoq" name="odd">' + lot_lang.bt_sel_odd + '</span><span class="dxjoq" name="even">' + lot_lang.bt_sel_even + '</span><span class="dxjoq" name="clean">' + lot_lang.bt_sel_clean + "</span></div>"
                            }
                            html += "</li>"
                        }
                    })
            } else {
                if (otype == "dxds") {
                    $.each(opts.layout,
                        function (i, n) {
                            n.place = parseInt(n.place, 10);
                            max_place = n.place > max_place ? n.place : max_place;
                            data_sel[n.place] = [];
                            html += "<li>";
                            if (n.cols > 0) {
                                html += "<div class='li_l color01'>";
                                if (n.title.length > 0) {
                                    html += n.title
                                }
                                html += "</div>"
                            }
                            html += '<div class="li_m">';
                            numbers = n.no.split("|");
                            j = numbers.length;
                            for (i = 0; i < j; i++) {
                                html += '<span name="lt_place_' + n.place + '">' + numbers[i] + "</span>"
                            }
                            html += '</div><div class="li_r"><span class="dxjoq" name="clean">' + lot_lang.bt_sel_clean + "</span></div></li>"
                        })
                }
            }
        }
        $html = $(html);
        $(this).empty();
        $html.appendTo(this);
        $($.lt_id_data.id_desc).html($.lt_method_data.methoddesc);
        $("#lt_desc__").html($.lt_method_data.methodhelp);
        $("#lt_desc_").html($.lt_method_data.methodexample);
        $(".selpositioninput").click(function () {
            $.lt_position_sel = [];
            $.each($(".selpositioninput"),
                function () {
                    positionvalue = $(this).attr("name");
                    positionvalue = positionvalue.split("_");
                    if ($(this).is(":checked")) {
                        $.lt_position_sel.push(positionvalue[1])
                    }
                });
            $("#positioncount").html($.lt_position_sel.length);
            var projectCount = $.lt_position_sel.length == 0 ? 0 : Combination($.lt_position_sel.length, $.lt_method_data.numcount);
            $("#positioninfo").html(projectCount);
            checkNum()
        });
        var me = this;
        var _SortNum = function (a, b) {
            if (otype != "input") {
                a = a.replace(/\u8c79\u5b50/g, 0).replace(/\u987a\u5b50/g, 1).replace(/\u5bf9\u5b50/g, 2);
                a = a.replace(/\u5927/g, 0).replace(/\u5c0f/g, 1).replace(/\u5355/g, 2).replace(/\u53cc/g, 3).replace(/\s/g, "");
                b = b.replace(/\u8c79\u5b50/g, 0).replace(/\u987a\u5b50/g, 1).replace(/\u5bf9\u5b50/g, 2);
                b = b.replace(/\u5927/g, 0).replace(/\u5c0f/g, 1).replace(/\u5355/g, 2).replace(/\u53cc/g, 3).replace(/\s/g, "")
            }
            a = parseInt(a, 10);
            b = parseInt(b, 10);
            if (isNaN(a) || isNaN(b)) {
                return true
            }
            return (a - b)
        };
        var _HHZXcheck = function (n, len) {
            if (len == 2) {
                var a = ["00", "11", "22", "33", "44", "55", "66", "77", "88", "99"]
            } else {
                var a = ["000", "111", "222", "333", "444", "555", "666", "777", "888", "999"]
            }
            n = n.toString();
            if ($.inArray(n, a) == -1) {
                return true
            }
            return false
        };
        var _ZUSDScheck = function (n, len) {
            if (len != 3) {
                return false
            }
            var first = "";
            var second = "";
            var third = "";
            var i = 0;
            for (i = 0; i < len; i++) {
                if (i == 0) {
                    first = n.substr(i, 1)
                }
                if (i == 1) {
                    second = n.substr(i, 1)
                }
                if (i == 2) {
                    third = n.substr(i, 1)
                }
            }
            if (first == second && second == third) {
                return false
            }
            if (first == second || second == third || third == first) {
                return true
            }
            return false
        };
        var _ZULDScheck = function (n, len) {
            if (len != 3) {
                return false
            }
            var first = "";
            var second = "";
            var third = "";
            var i = 0;
            for (i = 0; i < len; i++) {
                if (i == 0) {
                    first = n.substr(i, 1)
                }
                if (i == 1) {
                    second = n.substr(i, 1)
                }
                if (i == 2) {
                    third = n.substr(i, 1)
                }
            }
            if (first == second || second == third || third == first) {
                return false
            } else {
                return true
            }
            return false
        };
        var _inputCheck_Num = function (l, e, fun, sort) {
            var nums = data_sel[0].length;
            var error = [];
            var newsel = [];
            var partn = "";
            l = parseInt(l, 10);
            switch (l) {
                case 2:
                    partn = /^[0-9]{2}$/;
                    break;
                case 4:
                    partn = /^[0-9]{4}$/;
                    break;
                case 5:
                    partn = /^[0-9]{5}$/;
                    break;
                default:
                    partn = /^[0-9]{3}$/;
                    break
            }
            fun = $.isFunction(fun) ? fun : function (s) {
                return true
            };
            $.each(data_sel[0],
                function (i, n) {
                    n = $.trim(n);
                    if (partn.test(n) && fun(n, l)) {
                        if (sort) {
                            if (n.indexOf(" ") == -1) {
                                n = n.split("");
                                n.sort(_SortNum);
                                n = n.join("")
                            } else {
                                n = n.split(" ");
                                n.sort(_SortNum);
                                n = n.join(" ")
                            }
                        }
                        data_sel[0][i] = n;
                        newsel.push(n)
                    } else {
                        if (n.length > 0) {
                            error.push(n)
                        }
                        nums = nums - 1
                    }
                });
            if (e == true) {
                data_sel[0] = newsel;
                return error
            }
            return nums
        };

        function checkNum() {
            var nums = 0,
                mname = $.lt_method[$.lt_method_data.methodid];
            var modes = parseInt($($.lt_id_data.id_sel_modes).attr("value"), 10);
            var isrx = $.lt_method_data.isrx;
            if (otype == "input") {
                if (data_sel[0].length > 0) {
                    switch (mname) {
                        case "ZX5":
                            nums = _inputCheck_Num(5, false);
                            break;
                        case "ZX4":
                            nums = _inputCheck_Num(4, false);
                            break;
                        case "ZX3":
                            nums = _inputCheck_Num(3, false);
                            break;
                        case "ZUS":
                            nums = _inputCheck_Num(3, false, _ZUSDScheck, true);
                            if (isrx == 1) {
                                nums *= $.lt_position_sel.length == 0 ? 0 : Combination($.lt_position_sel.length, 3)
                            }
                            break;
                        case "ZUL":
                            nums = _inputCheck_Num(3, false, _ZULDScheck, true);
                            if (isrx == 1) {
                                nums *= $.lt_position_sel.length == 0 ? 0 : Combination($.lt_position_sel.length, 3)
                            }
                            break;
                        case "HHZX":
                            nums = _inputCheck_Num(3, false, _HHZXcheck, true);
                            if (isrx == 1) {
                                nums *= $.lt_position_sel.length == 0 ? 0 : Combination($.lt_position_sel.length, 3)
                            }
                            break;
                        case "ZX2":
                            nums = _inputCheck_Num(2, false);
                            break;
                        case "ZU2":
                            nums = _inputCheck_Num(2, false, _HHZXcheck, true);
                            if (isrx == 1) {
                                nums *= $.lt_position_sel.length == 0 ? 0 : Combination($.lt_position_sel.length, 2)
                            }
                            break;
                        case "RZX2":
                        case "RZX3":
                        case "RZX4":
                            var sellen = mname.substring(mname.length - 1);
                            nums = _inputCheck_Num(sellen, false);
                            nums *= $.lt_position_sel.length == 0 ? 0 : Combination($.lt_position_sel.length, sellen);
                            break;
                        default:
                            break
                    }
                }
            } else {
                var tmp_nums = 1;
                switch (mname) {
                    case "ZH5":
                    case "ZH4":
                    case "ZH3":
                        for (i = 0; i <= max_place; i++) {
                            if (data_sel[i].length == 0) {
                                tmp_nums = 0;
                                break
                            }
                            tmp_nums *= data_sel[i].length
                        }
                        nums = tmp_nums * parseInt(mname.charAt(mname.length - 1));
                        break;
                    case "WXZU120":
                        var s = data_sel[0].length;
                        if (s > 4) {
                            nums += Combination(s, 5)
                        }
                        break;
                    case "WXZU60":
                    case "WXZU30":
                    case "WXZU20":
                    case "WXZU10":
                    case "WXZU5":
                        if (data_sel[0].length >= minchosen[0] && data_sel[1].length >= minchosen[1]) {
                            var h = Array.intersect(data_sel[0], data_sel[1]).length;
                            tmp_nums = Combination(data_sel[0].length, minchosen[0]) * Combination(data_sel[1].length, minchosen[1]);
                            if (h > 0) {
                                if (mname == "WXZU60") {
                                    tmp_nums -= Combination(h, 1) * Combination(data_sel[1].length - 1, 2)
                                } else {
                                    if (mname == "WXZU30") {
                                        tmp_nums -= Combination(h, 2) * Combination(2, 1);
                                        if (data_sel[0].length - h > 0) {
                                            tmp_nums -= Combination(h, 1) * Combination(data_sel[0].length - h, 1)
                                        }
                                    } else {
                                        if (mname == "WXZU20") {
                                            tmp_nums -= Combination(h, 1) * Combination(data_sel[1].length - 1, 1)
                                        } else {
                                            if (mname == "WXZU10" || mname == "WXZU5") {
                                                tmp_nums -= Combination(h, 1)
                                            }
                                        }
                                    }
                                }
                            }
                            nums += tmp_nums
                        }
                        break;
                    case "SXZU24":
                        var s = data_sel[0].length;
                        if (s > 3) {
                            nums += Combination(s, 4)
                        }
                        if (isrx == 1) {
                            nums *= $.lt_position_sel.length == 0 ? 0 : Combination($.lt_position_sel.length, 4)
                        }
                        break;
                    case "SXZU6":
                        if (data_sel[0].length >= minchosen[0]) {
                            nums += Combination(data_sel[0].length, minchosen[0])
                        }
                        if (isrx == 1) {
                            nums *= $.lt_position_sel.length == 0 ? 0 : Combination($.lt_position_sel.length, 4)
                        }
                        break;
                    case "SXZU12":
                    case "SXZU4":
                        if (data_sel[0].length >= minchosen[0] && data_sel[1].length >= minchosen[1]) {
                            var h = Array.intersect(data_sel[0], data_sel[1]).length;
                            tmp_nums = Combination(data_sel[0].length, minchosen[0]) * Combination(data_sel[1].length, minchosen[1]);
                            if (h > 0) {
                                if (mname == "SXZU12") {
                                    tmp_nums -= Combination(h, 1) * Combination(data_sel[1].length - 1, 1)
                                } else {
                                    if (mname == "SXZU4") {
                                        tmp_nums -= Combination(h, 1)
                                    }
                                }
                            }
                            nums += tmp_nums
                        }
                        if (isrx == 1) {
                            nums *= $.lt_position_sel.length == 0 ? 0 : Combination($.lt_position_sel.length, 4)
                        }
                        break;
                    case "ZXKD":
                        var cc = {
                            0: 10,
                            1: 54,
                            2: 96,
                            3: 126,
                            4: 144,
                            5: 150,
                            6: 144,
                            7: 126,
                            8: 96,
                            9: 54
                        };
                        for (i = 0; i <= max_place; i++) {
                            var s = data_sel[i].length;
                            for (j = 0; j < s; j++) {
                                nums += cc[parseInt(data_sel[i][j], 10)]
                            }
                        }
                        break;
                    case "ZXKD2":
                        var cc = {
                            0: 10,
                            1: 18,
                            2: 16,
                            3: 14,
                            4: 12,
                            5: 10,
                            6: 8,
                            7: 6,
                            8: 4,
                            9: 2
                        };
                        for (i = 0; i <= max_place; i++) {
                            var s = data_sel[i].length;
                            for (j = 0; j < s; j++) {
                                nums += cc[parseInt(data_sel[i][j], 10)]
                            }
                        }
                        break;
                    case "ZXHZ":
                        var cc = {
                            0: 1,
                            1: 3,
                            2: 6,
                            3: 10,
                            4: 15,
                            5: 21,
                            6: 28,
                            7: 36,
                            8: 45,
                            9: 55,
                            10: 63,
                            11: 69,
                            12: 73,
                            13: 75,
                            14: 75,
                            15: 73,
                            16: 69,
                            17: 63,
                            18: 55,
                            19: 45,
                            20: 36,
                            21: 28,
                            22: 21,
                            23: 15,
                            24: 10,
                            25: 6,
                            26: 3,
                            27: 1
                        };
                    case "ZUHZ":
                        if (mname == "ZUHZ") {
                            cc = {
                                1: 1,
                                2: 2,
                                3: 2,
                                4: 4,
                                5: 5,
                                6: 6,
                                7: 8,
                                8: 10,
                                9: 11,
                                10: 13,
                                11: 14,
                                12: 14,
                                13: 15,
                                14: 15,
                                15: 14,
                                16: 14,
                                17: 13,
                                18: 11,
                                19: 10,
                                20: 8,
                                21: 6,
                                22: 5,
                                23: 4,
                                24: 2,
                                25: 2,
                                26: 1
                            }
                        }
                        for (i = 0; i <= max_place; i++) {
                            var s = data_sel[i].length;
                            for (j = 0; j < s; j++) {
                                nums += cc[parseInt(data_sel[i][j], 10)]
                            }
                        }
                        if (isrx == 1) {
                            nums *= $.lt_position_sel.length == 0 ? 0 : Combination($.lt_position_sel.length, 3)
                        }
                        break;
                    case "ZUS":
                        for (i = 0; i <= max_place; i++) {
                            var s = data_sel[i].length;
                            if (s > 1) {
                                nums += s * (s - 1)
                            }
                        }
                        if (isrx == 1) {
                            nums *= $.lt_position_sel.length == 0 ? 0 : Combination($.lt_position_sel.length, 3)
                        }
                        break;
                    case "ZUL":
                        for (i = 0; i <= max_place; i++) {
                            var s = data_sel[i].length;
                            if (s > 2) {
                                nums += s * (s - 1) * (s - 2) / 6
                            }
                        }
                        if (isrx == 1) {
                            nums *= $.lt_position_sel.length == 0 ? 0 : Combination($.lt_position_sel.length, 3)
                        }
                        break;
                    case "ZXHZ2":
                        cc = {
                            0: 1,
                            1: 2,
                            2: 3,
                            3: 4,
                            4: 5,
                            5: 6,
                            6: 7,
                            7: 8,
                            8: 9,
                            9: 10,
                            10: 9,
                            11: 8,
                            12: 7,
                            13: 6,
                            14: 5,
                            15: 4,
                            16: 3,
                            17: 2,
                            18: 1
                        };
                        for (i = 0; i <= max_place; i++) {
                            var s = data_sel[i].length;
                            for (j = 0; j < s; j++) {
                                nums += cc[parseInt(data_sel[i][j], 10)]
                            }
                        }
                        if (isrx == 1) {
                            nums *= $.lt_position_sel.length == 0 ? 0 : Combination($.lt_position_sel.length, 2)
                        }
                        break;
                    case "ZUHZ2":
                        cc = {
                            0: 0,
                            1: 1,
                            2: 1,
                            3: 2,
                            4: 2,
                            5: 3,
                            6: 3,
                            7: 4,
                            8: 4,
                            9: 5,
                            10: 4,
                            11: 4,
                            12: 3,
                            13: 3,
                            14: 2,
                            15: 2,
                            16: 1,
                            17: 1,
                            18: 0
                        };
                        for (i = 0; i <= max_place; i++) {
                            var s = data_sel[i].length;
                            for (j = 0; j < s; j++) {
                                nums += cc[parseInt(data_sel[i][j], 10)]
                            }
                        }
                        if (isrx == 1) {
                            nums *= $.lt_position_sel.length == 0 ? 0 : Combination($.lt_position_sel.length, 2)
                        }
                        break;
                    case "ZU3BD":
                        nums = data_sel[0].length * 54;
                        break;
                    case "ZU2BD":
                        nums = data_sel[0].length * 9;
                        break;
                    case "BDW3":
                        for (i = 0; i <= max_place; i++) {
                            var s = data_sel[i].length;
                            if (s > 2) {
                                nums += Combination(data_sel[i].length, 3)
                            }
                        }
                        break;
                    case "BDW2":
                    case "ZU2":
                        for (i = 0; i <= max_place; i++) {
                            var s = data_sel[i].length;
                            if (s > 1) {
                                nums += s * (s - 1) / 2
                            }
                        }
                        if (isrx == 1) {
                            nums *= $.lt_position_sel.length == 0 ? 0 : Combination($.lt_position_sel.length, 2)
                        }
                        break;
                    case "DWD":
                        for (i = 0; i <= max_place; i++) {
                            nums += data_sel[i].length
                        }
                        break;
                    case "RZX2":
                    case "RZX3":
                    case "RZX4":
                        var aCodePosition = [];
                        for (i = 0; i <= max_place; i++) {
                            var codelen = data_sel[i].length;
                            if (codelen > 0) {
                                aCodePosition.push(i)
                            }
                        }
                        var sellen = mname.substring(mname.length - 1);
                        var aPositionCombo = getCombination(aCodePosition, sellen);
                        var iComboLen = aPositionCombo.length;
                        var aCombo = [];
                        var iLen = 0;
                        var tmpNums = 1;
                        for (j = 0; j < iComboLen; j++) {
                            aCombo = aPositionCombo[j].split(",");
                            iLen = aCombo.length;
                            tmpNums = 1;
                            for (h = 0; h < iLen; h++) {
                                tmpNums *= data_sel[aCombo[h]].length
                            }
                            nums += tmpNums
                        }
                        break;
                    default:
                        for (i = 0; i <= max_place; i++) {
                            if (data_sel[i].length == 0) {
                                tmp_nums = 0;
                                break;
                                break
                            }
                            tmp_nums *= data_sel[i].length
                        }
                        nums = tmp_nums;
                        break
                }
            }
            var times = parseInt($($.lt_id_data.id_sel_times).val(), 10);
            if (isNaN(times)) {
                times = 1;
                $($.lt_id_data.id_sel_times).val(1)
            }
            var money = Math.round(times * nums * 2 * ($.lt_method_data.modes[modes].rate * 1000)) / 1000;
            money = isNaN(money) ? 0 : money;
            $($.lt_id_data.id_sel_num).html(nums);
            $($.lt_id_data.id_sel_money).html(money)
        }

        var dumpNum = function (isdeal) {
            var l = data_sel[0].length;
            var err = [];
            var news = [];
            if (l == 0) {
                return err
            }
            if (l <= 5000) {
                for (i = 0; i < l; i++) {
                    if ($.inArray(data_sel[0][i], err) != -1) {
                        continue
                    }
                    for (j = i + 1; j < l; j++) {
                        if (data_sel[0][i] == data_sel[0][j]) {
                            err.push(data_sel[0][i]);
                            break
                        }
                    }
                    news.push(data_sel[0][i])
                }
                if (isdeal) {
                    data_sel[0] = news
                }
            }
            return err
        };

        function _inptu_deal() {
            var s = $.trim($("#lt_write_box", $(me)).val());
            s = $.trim(s.replace(/[^\s\r,;\uff0c\uff1b\u3000\uff10\uff11\uff12\uff13\uff14\uff15\uff16\uff17\uff18\uff190-9]/g, ""));
            var m = s;
            switch (methodname) {
                default:
                    s = s.replace(/[\s\r,;\uff0c\uff1b\u3000]/g, "|").replace(/(\|)+/g, "|");
                    break
            }
            s = s.replace(/\uff10/g, "0").replace(/\uff11/g, "1").replace(/\uff12/g, "2").replace(/\uff13/g, "3").replace(/\uff14/g, "4").replace(/\uff15/g, "5").replace(/\uff16/g, "6").replace(/\uff17/g, "7").replace(/\uff18/g, "8").replace(/\uff19/g, "9");
            if (s == "") {
                data_sel[0] = []
            } else {
                data_sel[0] = s.split("|")
            }
            return m
        }

        if (otype == "input") {
            $("#lt_write_del", $(me)).click(function () {
                var err = dumpNum(true);
                if (err.length > 0) {
                    checkNum();
                    switch (methodname) {
                        default:
                            $("#lt_write_box", $(me)).val(data_sel[0].join(" "));
                            $.alert('<div class="datainfo">' + lot_lang.am_s3 + "\r" + err.join(" ") + "\r&nbsp;</div>", "", "", 400);
                            break
                    }
                } else {
                    $.alert(lot_lang.am_s4)
                }
            });
            $("#lt_write_import", $(me)).click(function () {
                $.ajaxUploadUI({
                    title: lot_lang.dec_s27,
                    url: "/FileUpload.shtml",
                    loadok: lot_lang.dec_s28,
                    filetype: ["txt", "csv"],
                    success: function (data) {
                        $("#lt_write_box", $(me)).val(data).change()
                    },
                    onfinish: function () {
                        $("#lt_write_box", $(me)).focus()
                    }
                })
            });
            $("#lt_write_box", $(me)).change(function () {
                var s = _inptu_deal();
                $(this).val(s);
                checkNum()
            }).keyup(function () {
                _inptu_deal();
                checkNum()
            });
            $("#lt_write_empty", $(me)).click(function () {
                data_sel[0] = [];
                $("#lt_write_box", $(me)).val("");
                checkNum()
            })
        }
        function selectNum(obj, isButton) {
            if ($.trim($(obj).attr("class")) == "on") {
                return
            }
            $(obj).attr("class", "on");
            place = Number($(obj).attr("name").replace("lt_place_", ""));
            var number = $.trim($(obj).text());
            data_sel[place].push(number);
            if (!isButton) {
                var numlimit = parseInt($.lt_method_data.maxcodecount);
                if (numlimit > 0) {
                    if (data_sel[place].length > numlimit) {
                        $.each($(obj).parent().find("[name^='lt_place_']"),
                            function (i, n) {
                                unSelectNum(n, false)
                            });
                        selectNum(obj, false)
                    }
                }
                checkNum()
            }
        }

        function unSelectNum(obj, isButton) {
            if ($.trim($(obj).attr("class")) != "on") {
                return
            }
            $(obj).attr("class", "");
            place = Number($(obj).attr("name").replace("lt_place_", ""));
            var number = $.trim($(obj).text());
            data_sel[place] = $.grep(data_sel[place],
                function (n, i) {
                    return n == number
                },
                true);
            if (!isButton) {
                checkNum()
            }
        }

        function changeNoCss(obj) {
            if ($.trim($(obj).attr("class")) == "on") {
                unSelectNum(obj, false)
            } else {
                selectNum(obj, false)
            }
        }

        function selectOdd(obj) {
            if (Number($(obj).text()) % 2 == 1) {
                selectNum(obj, true)
            } else {
                unSelectNum(obj, true)
            }
        }

        function selectEven(obj) {
            if (Number($(obj).text()) % 2 == 0) {
                selectNum(obj, true)
            } else {
                unSelectNum(obj, true)
            }
        }

        function selectBig(i, obj) {
            if (i >= opts.noBigIndex) {
                selectNum(obj, true)
            } else {
                unSelectNum(obj, true)
            }
        }

        function selectSmall(i, obj) {
            if (i < opts.noBigIndex) {
                selectNum(obj, true)
            } else {
                unSelectNum(obj, true)
            }
        }

        $(this).find("[name^='lt_place_']").click(function () {
            changeNoCss(this);
            $("[class^='dxjoq']", $(this).closest("li")).attr("class", "dxjoq")
        });
        if (opts.isButton == true || otype == "dxds") {
            $("[class='dxjoq']", $(this)).click(function () {
                $("[class^='dxjoq']", $(this).parent()).attr("class", "dxjoq");
                $(this).attr("class", "dxjoq on");
                switch ($(this).attr("name")) {
                    case "all":
                        $.each($(this).closest("li").find("[name^='lt_place_']"),
                            function (i, n) {
                                selectNum(n, true)
                            });
                        break;
                    case "big":
                        $.each($(this).closest("li").find("[name^='lt_place_']"),
                            function (i, n) {
                                selectBig(i, n)
                            });
                        break;
                    case "small":
                        $.each($(this).closest("li").find("[name^='lt_place_']"),
                            function (i, n) {
                                selectSmall(i, n)
                            });
                        break;
                    case "odd":
                        $.each($(this).closest("li").find("[name^='lt_place_']"),
                            function (i, n) {
                                selectOdd(n)
                            });
                        break;
                    case "even":
                        $.each($(this).closest("li").find("[name^='lt_place_']"),
                            function (i, n) {
                                selectEven(n)
                            });
                        break;
                    case "clean":
                        $.each($(this).closest("li").find("[name^='lt_place_']"),
                            function (i, n) {
                                unSelectNum(n, true)
                            });
                    default:
                        break
                }
                checkNum()
            })
        }
        $($.lt_id_data.id_sel_times).unbind("keyup").keyup(function () {
            var times = $(this).val().replace(/[^0-9]/g, "").substring(0, 5);
            if (times == "") {
                times = 0
            } else {
                times = parseInt(times, 10)
            }
            var nums = parseInt($($.lt_id_data.id_sel_num).html(), 10);
            var modes = parseInt($($.lt_id_data.id_sel_modes).attr("value"), 10);
            var money = Math.round(times * nums * 2 * ($.lt_method_data.modes[modes].rate * 1000)) / 1000;
            money = isNaN(money) ? 0 : money;
            $($.lt_id_data.id_sel_money).html(money);
            $(this).val(times)
        });
        $($.lt_id_data.id_sel_times).nextAll("a").click(function () {
            $($.lt_id_data.id_sel_times).val($(this).html()).keyup()
        });
        $($.lt_id_data.id_reduce_times).unbind("click").click(function () {
            var times = Math.round(parseInt($($.lt_id_data.id_sel_times).val(), 10) - 1);
            if (times < 1) {
                times = 1
            }
            $($.lt_id_data.id_sel_times).val(times).keyup()
        });
        $($.lt_id_data.id_plus_times).unbind("click").click(function () {
            var times = Math.round(parseInt($($.lt_id_data.id_sel_times).val(), 10) + 1);
            if (times > 99999) {
                times = 99999
            }
            $($.lt_id_data.id_sel_times).val(times).keyup()
        });
        var accMul = function (arg1, arg2) {
            var m = 0,
                s1 = arg1.toString(),
                s2 = arg2.toString();
            try {
                m += s1.split(".")[1].length
            } catch (e) {
            }
            try {
                m += s2.split(".")[1].length
            } catch (e) {
            }
            return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)
        };
        $($.lt_id_data.lt_tra_count).bind("change",
            function () {
                var traceCount = parseInt($(this).val(), 10);
                $.lt_total_money_trace = accMul($.lt_trace_base, traceCount);
                $($.lt_id_data.id_cf_money).html($.lt_total_money_trace)
            });
        $($.lt_id_data.id_sel_insert).unbind("click").click(function () {
            var nums = parseInt($($.lt_id_data.id_sel_num).html(), 10);
            var times = parseInt($($.lt_id_data.id_sel_times).val(), 10);
            var modes = parseInt($($.lt_id_data.id_sel_modes).attr("value"), 10);
            var money = Math.round(times * nums * 2 * ($.lt_method_data.modes[modes].rate * 1000)) / 1000;
            var mid = $.lt_method_data.methodid;
            var current_positionsel = $.lt_position_sel;
            var current_methodtitle = $.lt_method_data.title;
            var current_methodname = $.lt_method_data.name;
            if (current_positionsel.length > 0 && $.lt_rxmode == true) {
                if (current_positionsel.length < $.lt_method_data.numcount) {
                    $.alert(lot_lang.am_s37.replace("%s", $.lt_method_data.numcount).replace("%m", current_methodtitle));
                    return
                }
            }
            var cur_position = 0;
            if (current_positionsel.length > 0) {
                $.each(current_positionsel,
                    function (i, n) {
                        cur_position += Math.pow(2, 4 - parseInt(n, 10))
                    })
            }
            if (isNaN(nums) || isNaN(times) || isNaN(money) || money <= 0) {
                $.alert(otype == "input" ? lot_lang.am_s29 : lot_lang.am_s19);
                return
            }
            if (otype == "input") {
                var mname = $.lt_method[mid];
                var error = [];
                var edump = [];
                var ermsg = "";
                edump = dumpNum(true);
                if (edump.length > 0) {
                    ermsg += lot_lang.em_s2 + "\n" + edump.join(", ") + "\r&nbsp;";
                    checkNum();
                    nums = parseInt($($.lt_id_data.id_sel_num).html(), 10);
                    money = Math.round(times * nums * 2 * ($.lt_method_data.modes[modes].rate * 1000)) / 1000
                }
                switch (mname) {
                    case "ZX5":
                        error = _inputCheck_Num(5, true);
                        break;
                    case "ZX4":
                    case "RZX4":
                        error = _inputCheck_Num(4, true);
                        break;
                    case "ZX3":
                    case "RZX3":
                        error = _inputCheck_Num(3, true);
                        break;
                    case "HHZX":
                        error = _inputCheck_Num(3, true, _HHZXcheck, true);
                        break;
                    case "ZX2":
                    case "RZX2":
                        error = _inputCheck_Num(2, true);
                        break;
                    case "ZU2":
                        error = _inputCheck_Num(2, true, _HHZXcheck, true);
                        break;
                    case "ZUS":
                        error = _inputCheck_Num(3, true, _ZUSDScheck, true);
                        break;
                    case "ZUL":
                        error = _inputCheck_Num(3, true, _ZULDScheck, true);
                        break;
                    default:
                        break
                }
                if (error.length > 0) {
                    ermsg += lot_lang.em_s1 + "\n" + error.join(", ") + "\r&nbsp;"
                }
                if (ermsg.length > 1) {
                    $.alert("<div class='datainfo'>" + ermsg + "</div>", "", "", 400)
                }
            }
            var nos = $.lt_method_data.str;
            var serverdata = "{'type':'" + otype + "','methodid':" + mid + ",'codes':'";
            var temp = [];
            for (i = 0; i < data_sel.length; i++) {
                if (otype == "input") {
                    nos = nos.replace("X", data_sel[i].sort(_SortNum).join("|"))
                } else {
                    nos = nos.replace("X", data_sel[i].sort(_SortNum).join($.lt_method_data.sp))
                }
                temp.push(data_sel[i].sort(_SortNum).join("&"))
            }
            if (nos.length > 40) {
                var nohtml = nos.substring(0, 35) + "..."
            } else {
                var nohtml = nos
            }
            if ($.lt_same_code[mid] != undefined && $.lt_same_code[mid][modes] != undefined && $.lt_same_code[mid][modes][cur_position] != undefined && $.lt_same_code[mid][modes][cur_position].length > 0) {
                if ($.inArray(temp.join("|"), $.lt_same_code[mid][modes][cur_position]) != -1) {
                    $.alert(lot_lang.am_s28);
                    return false
                }
            }
            var sel_isdy = false;
            var sel_prize = 0;
            var sel_point = 1;
            if ($.lt_method_data.dyprize.length == 1 && $.lt_isdyna == 1) {
                if ($("#lt_sel_dyprize") == undefined) {
                    $.alert(lot_lang.am_s27);
                    return false
                }
                var sel_dy = $("#lt_sel_dyprize").val();
                sel_dy = sel_dy.split("|");
                if (sel_dy[1] == undefined) {
                    $.alert(lot_lang.am_s27);
                    return false
                }
                sel_isdy = true;
                sel_prize = Math.round(Number(sel_dy[0]) * ($.lt_method_data.modes[modes].rate * 1000)) / 1000;
                sel_point = Number(sel_dy[1])
            }
            noshtml = "[" + $.lt_method_data.title + "_" + $.lt_method_data.name + "] " + nohtml;
            if ($.lt_method[mid] == "DXDS") {
                noshtml = "[" + $.lt_method_data.name + "] " + nohtml
            }
            var myDate = new Date();
            var curTimes = myDate.getTime();
            var code01 = temp.join("|");
            var zipFlag = "0";
            var codesZip = "";
            var zipConFlag = false;
            if (otype == "input") {
                if (nums > 5000) {
                    zipConFlag = true
                }
            }
            if (zipConFlag) {
                var compression_mode = 6,
                    my_lzma = new LZMA("/js/lzma/lzma_worker.js");
                my_lzma.compress(code01, compression_mode,
                    function (result) {
                        codesZip = convert_to_formated_hex(result);
                        if (current_positionsel.length > 0) {
                            serverdata += codesZip + "','zip':" + 1 + ",'nums':" + nums + ",'times':" + times + ",'money':" + money + ",'mode':" + modes + ",'point':'" + sel_point + "','desc':'" + noshtml + "','position':'" + current_positionsel.join("&") + "','curtimes':'" + curTimes + "'}"
                        } else {
                            serverdata += codesZip + "','zip':" + 1 + ",'nums':" + nums + ",'times':" + times + ",'money':" + money + ",'mode':" + modes + ",'point':'" + sel_point + "','desc':'" + noshtml + "','curtimes':'" + curTimes + "'}"
                        }
                        var _codes = "";
                        if (temp.length == 1) {
                            _codes = temp.join("").replace(/&/g, "|")
                        } else {
                            _codes = temp.join("|").replace(/&/g, "")
                        }
                        var cfhtml = [];
                        cfhtml.push('<li class="item">');
                        cfhtml.push('<span class="td01">' + ($.lt_method_data.title + "_" + $.lt_method_data.name) + "</span>");
                        cfhtml.push('<span class="td02">' + _codes + "</span>");
                        cfhtml.push('<span class="td03">' + $.lt_method_data.modes[modes].name + "</span>");
                        cfhtml.push('<span class="td04">' + nums + "</span>");
                        cfhtml.push('<span class="td05">' + times + "</span>");
                        cfhtml.push('<span class="td06">' + money + lot_lang.dec_s3 + "</span>");
                        cfhtml.push('<span class="td07 last"><img src="images/cathectic/pic12.png"/></span>');
                        cfhtml.push('<input type="hidden" name="lt_project[]" value="' + serverdata + '" /></li>');
                        var $cfhtml = $(cfhtml.join(""));
                        if ($.lt_total_nums == 0) {
                            $($.lt_id_data.id_cf_content).children().filter(".item").remove()
                        }
                        $($.lt_id_data.id_cf_content).find("li.first").after($cfhtml);
                        $(".td01", $cfhtml).parent().click(function () {
                            var aPositionTile = $.lt_position_title;
                            var iPositionLen = current_positionsel.length;
                            var positionname = "";
                            if (iPositionLen > 0) {
                                positionname += "<br/>" + lot_lang.dec_s40;
                                for (var i = 0; i < iPositionLen; i++) {
                                    positionname += aPositionTile[current_positionsel[i]];
                                    if (i < iPositionLen - 1) {
                                        positionname += "\u3001"
                                    }
                                }
                            }
                            var sss = '<h4 style="text-align:left;">' + lot_lang.dec_s30 + ": " + current_methodtitle + "_" + current_methodname + positionname + "<br/>" + lot_lang.dec_s32 + ": " + $.lt_method_data.modes[modes].name + lot_lang.dec_s32 + (sel_isdy ? (", " + lot_lang.dec_s33 + " " + sel_prize + ", " + lot_lang.dec_s34 + " " + (Math.ceil(sel_point * 1000) / 10) + "%") : "") + "<br/>" + lot_lang.dec_s35 + " " + nums + " " + lot_lang.dec_s1 + ", " + times + " " + lot_lang.dec_s2 + ", " + lot_lang.dec_s36 + " " + money + " " + lot_lang.dec_s3;
                            var methodcode = $.lt_method[mid];
                            var tmpcodenos = "";
                            var dataheight = 60;
                            switch (methodcode) {
                                case "RZX2":
                                case "RZX3":
                                case "RZX4":
                                    if (otype == "input") {
                                        tmpcodenos = nos;
                                        sss += "</h4>"
                                    } else {
                                        var aAllCode = nos.split(",");
                                        var iCodeLen = aAllCode.length;
                                        var len = 0;
                                        var aCodePosition = [];
                                        for (i = 0; i < iCodeLen; i++) {
                                            len = aAllCode[i].length;
                                            if (len > 0) {
                                                aCodePosition.push(i)
                                            }
                                        }
                                        var sellen = methodcode.substring(methodcode.length - 1);
                                        var aPositionCombo = getCombination(aCodePosition, sellen);
                                        var iComboLen = aPositionCombo.length;
                                        dataheight = iComboLen < 5 ? 60 : iComboLen * 15;
                                        var aCombo = [];
                                        var iLen = 0;
                                        for (j = 0; j < iComboLen; j++) {
                                            aCombo = aPositionCombo[j].split(",");
                                            iLen = aCombo.length;
                                            var tmpnum = "";
                                            var tmptitle = "";
                                            var tmpnums = 1;
                                            for (h = 0; h < iLen; h++) {
                                                tmpnum += aAllCode[aCombo[h]];
                                                tmpnums *= aAllCode[aCombo[h]].length;
                                                tmptitle += aPositionTile[aCombo[h]];
                                                if (h < iLen - 1) {
                                                    tmpnum += ",";
                                                    tmptitle += "\u3001"
                                                }
                                            }
                                            tmpcodenos += "[" + tmptitle + "]  " + tmpnum + "&nbsp;&nbsp;|&nbsp;&nbsp;" + tmpnums + lot_lang.dec_s1;
                                            if (j < iComboLen - 1) {
                                                tmpcodenos += "<br>"
                                            }
                                        }
                                        sss += " , " + lot_lang.dec_s36 + iComboLen + lot_lang.dec_s38;
                                        sss += "<br><font color=red>" + lot_lang.dec_s39 + "</font></h4>"
                                    }
                                    break;
                                default:
                                    sss += "</h4>";
                                    tmpcodenos = nos;
                                    break
                            }
                            sss += '<div class="data" style="height:' + dataheight + 'px;"><table border=0 cellspacing=0 cellpadding=0><tr><td>' + tmpcodenos + "</td></tr></table></div>";
                            $.alert(sss, lot_lang.dec_s5, "", 450, false)
                        });
                        $.lt_total_nums += nums;
                        $.lt_total_money += money;
                        $.lt_total_money = Math.round($.lt_total_money * 1000) / 1000;
                        basemoney = Math.round(nums * 2 * ($.lt_method_data.modes[modes].rate * 1000)) / 1000;
                        $.lt_trace_base = Math.round(($.lt_trace_base + basemoney) * 1000) / 1000;
                        $($.lt_id_data.id_cf_num).html($.lt_total_nums);
                        $.lt_total_money_trace = accMul($.lt_total_money, parseInt($($.lt_id_data.lt_tra_count).val(), 10));
                        $($.lt_id_data.id_cf_money).html($.lt_total_money_trace);
                        $($.lt_id_data.id_cf_count).html(parseInt($($.lt_id_data.id_cf_count).html(), 10) + 1);
                        var pc = 0;
                        var pz = 0;
                        $.each($.lt_method_data.prize,
                            function (i, n) {
                                n = isNaN(Number(n)) ? 0 : Number(n);
                                pz = pz > n ? pz : n;
                                pc++
                            });
                        if (pc != 1) {
                            pz = 0
                        }
                        pz = Math.round(pz * ($.lt_method_data.modes[modes].rate * 1000)) / 1000;
                        pz = sel_isdy ? sel_prize : pz;
                        var aPositionTile = $.lt_position_title;
                        var iPositionLen = current_positionsel.length;
                        var positiondesc = "";
                        if (iPositionLen > 0) {
                            for (var i = 0; i < iPositionLen; i++) {
                                positiondesc += aPositionTile[current_positionsel[i]];
                                if (i < iPositionLen - 1) {
                                    positiondesc += "\u3001"
                                }
                            }
                        }
                        $cfhtml.data("data", {
                            methodid: mid,
                            methodname: $.lt_method_data.title + "_" + $.lt_method_data.name,
                            nums: nums,
                            money: money,
                            modes: modes,
                            position: cur_position,
                            positiondesc: positiondesc,
                            modename: $.lt_method_data.modes[modes].name,
                            basemoney: basemoney,
                            prize: pz,
                            code: temp.join("|"),
                            desc: nohtml,
                            isrx: $.lt_method_data.isrx
                        });
                        if ($.lt_same_code[mid] == undefined) {
                            $.lt_same_code[mid] = []
                        }
                        if ($.lt_same_code[mid][modes] == undefined) {
                            $.lt_same_code[mid][modes] = []
                        }
                        if ($.lt_same_code[mid][modes][cur_position] == undefined) {
                            $.lt_same_code[mid][modes][cur_position] = []
                        }
                        $.lt_same_code[mid][modes][cur_position].push(temp.join("|"));
                        $(".td07", $cfhtml).attr("title", lot_lang.dec_s24).click(function () {
                            var n = $cfhtml.data("data").nums;
                            var m = $cfhtml.data("data").money;
                            var b = $cfhtml.data("data").basemoney;
                            var c = $cfhtml.data("data").code;
                            var d = $cfhtml.data("data").methodid;
                            var f = $cfhtml.data("data").modes;
                            var p = $cfhtml.data("data").position;
                            var i = null;
                            $.each($.lt_same_code[d][f][p],
                                function (k, code) {
                                    if (code == c) {
                                        i = k
                                    }
                                });
                            if (i != null) {
                                $.lt_same_code[d][f][p].splice(i, 1)
                            } else {
                                $.alert(lot_lang.am_s27);
                                return
                            }
                            $.lt_total_nums -= n;
                            $.lt_total_money -= m;
                            $.lt_total_money = Math.round($.lt_total_money * 1000) / 1000;
                            $.lt_trace_base = Math.round(($.lt_trace_base - b) * 1000) / 1000;
                            $(this).parent().remove();
                            if ($.lt_total_nums == 0) {
                                $($.lt_id_data.id_cf_content).append('<li class="item none">暂无投注项</li>')
                            }
                            $($.lt_id_data.id_cf_num).html($.lt_total_nums);
                            $.lt_total_money_trace = accMul($.lt_total_money, parseInt($($.lt_id_data.lt_tra_count).val(), 10));
                            $($.lt_id_data.id_cf_money).html($.lt_total_money_trace);
                            $($.lt_id_data.id_cf_count).html(parseInt($($.lt_id_data.id_cf_count).html(), 10) - 1);
                            if ($.lt_ismargin == false) {
                                traceCheckMarginSup()
                            }
                            $(this).parent().closeFloat()
                        });
                        SetCookie("modes", modes, 86400);
                        SetCookie("dypoint", sel_point, 86400);
                        for (i = 0; i < data_sel.length; i++) {
                            data_sel[i] = []
                        }
                        if (otype == "input") {
                            $("#lt_write_box", $(me)).val("")
                        } else {
                            if (otype == "digital" || otype == "dxds" || otype == "dds" || otype == "digitalts") {
                                $("span,div", $(me)).filter(".on").removeClass("on")
                            }
                        }
                        $($.lt_id_data.id_sel_times).val(1);
                        checkNum();
                        if ($.lt_ismargin == true) {
                            traceCheckMarginSup()
                        }
                    })
            } else {
                if (current_positionsel.length > 0) {
                    serverdata += temp.join("|") + "','zip':" + 0 + ",'nums':" + nums + ",'times':" + times + ",'money':" + money + ",'mode':" + modes + ",'point':'" + sel_point + "','desc':'" + noshtml + "','position':'" + current_positionsel.join("&") + "','curtimes':'" + curTimes + "'}"
                } else {
                    serverdata += temp.join("|") + "','zip':" + 0 + ",'nums':" + nums + ",'times':" + times + ",'money':" + money + ",'mode':" + modes + ",'point':'" + sel_point + "','desc':'" + noshtml + "','curtimes':'" + curTimes + "'}"
                }
                var _codes = "";
                if (temp.length == 1) {
                    _codes = temp.join("").replace(/&/g, "|")
                } else {
                    _codes = temp.join("|").replace(/&/g, "")
                }
                var cfhtml = [];
                cfhtml.push('<li class="item">');
                cfhtml.push('<span class="td01">' + ($.lt_method_data.title + "_" + $.lt_method_data.name) + "</span>");
                cfhtml.push('<span class="td02">' + _codes + "</span>");
                cfhtml.push('<span class="td03">' + $.lt_method_data.modes[modes].name + "</span>");
                cfhtml.push('<span class="td04">' + nums + "</span>");
                cfhtml.push('<span class="td05">' + times + "</span>");
                cfhtml.push('<span class="td06">' + money + lot_lang.dec_s3 + "</span>");
                cfhtml.push('<span class="td07 last"><img src="images/cathectic/pic12.png"/></span>');
                cfhtml.push('<input type="hidden" name="lt_project[]" value="' + serverdata + '" /></li>');
                var $cfhtml = $(cfhtml.join(""));
                if ($.lt_total_nums == 0) {
                    $($.lt_id_data.id_cf_content).children().filter(".item").remove()
                }
                $($.lt_id_data.id_cf_content).find("li.first").after($cfhtml);
                $(".td01", $cfhtml).parent().click(function () {
                    var aPositionTile = $.lt_position_title;
                    var iPositionLen = current_positionsel.length;
                    var positionname = "";
                    if (iPositionLen > 0) {
                        positionname += "<br/>" + lot_lang.dec_s40;
                        for (var i = 0; i < iPositionLen; i++) {
                            positionname += aPositionTile[current_positionsel[i]];
                            if (i < iPositionLen - 1) {
                                positionname += "\u3001"
                            }
                        }
                    }
                    var sss = '<h4 style="text-align:left;">' + lot_lang.dec_s30 + ": " + current_methodtitle + "_" + current_methodname + positionname + "<br/>" + lot_lang.dec_s32 + ": " + $.lt_method_data.modes[modes].name + lot_lang.dec_s32 + (sel_isdy ? (", " + lot_lang.dec_s33 + " " + sel_prize + ", " + lot_lang.dec_s34 + " " + (Math.ceil(sel_point * 1000) / 10) + "%") : "") + "<br/>" + lot_lang.dec_s35 + " " + nums + " " + lot_lang.dec_s1 + ", " + times + " " + lot_lang.dec_s2 + ", " + lot_lang.dec_s36 + " " + money + " " + lot_lang.dec_s3;
                    var methodcode = $.lt_method[mid];
                    var tmpcodenos = "";
                    var dataheight = 60;
                    switch (methodcode) {
                        case "RZX2":
                        case "RZX3":
                        case "RZX4":
                            if (otype == "input") {
                                tmpcodenos = nos;
                                sss += "</h4>"
                            } else {
                                var aAllCode = nos.split(",");
                                var iCodeLen = aAllCode.length;
                                var len = 0;
                                var aCodePosition = [];
                                for (i = 0; i < iCodeLen; i++) {
                                    len = aAllCode[i].length;
                                    if (len > 0) {
                                        aCodePosition.push(i)
                                    }
                                }
                                var sellen = methodcode.substring(methodcode.length - 1);
                                var aPositionCombo = getCombination(aCodePosition, sellen);
                                var iComboLen = aPositionCombo.length;
                                dataheight = iComboLen < 5 ? 60 : iComboLen * 15;
                                var aCombo = [];
                                var iLen = 0;
                                for (j = 0; j < iComboLen; j++) {
                                    aCombo = aPositionCombo[j].split(",");
                                    iLen = aCombo.length;
                                    var tmpnum = "";
                                    var tmptitle = "";
                                    var tmpnums = 1;
                                    for (h = 0; h < iLen; h++) {
                                        tmpnum += aAllCode[aCombo[h]];
                                        tmpnums *= aAllCode[aCombo[h]].length;
                                        tmptitle += aPositionTile[aCombo[h]];
                                        if (h < iLen - 1) {
                                            tmpnum += ",";
                                            tmptitle += "\u3001"
                                        }
                                    }
                                    tmpcodenos += "[" + tmptitle + "]  " + tmpnum + "&nbsp;&nbsp;|&nbsp;&nbsp;" + tmpnums + lot_lang.dec_s1;
                                    if (j < iComboLen - 1) {
                                        tmpcodenos += "<br>"
                                    }
                                }
                                sss += " , " + lot_lang.dec_s36 + iComboLen + lot_lang.dec_s38;
                                sss += "<br><font color=red>" + lot_lang.dec_s39 + "</font></h4>"
                            }
                            break;
                        default:
                            sss += "</h4>";
                            tmpcodenos = nos;
                            break
                    }
                    sss += '<div class="data" style="height:' + dataheight + 'px;"><table border=0 cellspacing=0 cellpadding=0><tr><td>' + tmpcodenos + "</td></tr></table></div>";
                    $.alert(sss, lot_lang.dec_s5, "", 450, false)
                });
                $.lt_total_nums += nums;
                $.lt_total_money += money;
                $.lt_total_money = Math.round($.lt_total_money * 1000) / 1000;
                basemoney = Math.round(nums * 2 * ($.lt_method_data.modes[modes].rate * 1000)) / 1000;
                $.lt_trace_base = Math.round(($.lt_trace_base + basemoney) * 1000) / 1000;
                $($.lt_id_data.id_cf_num).html($.lt_total_nums);
                $.lt_total_money_trace = accMul($.lt_total_money, parseInt($($.lt_id_data.lt_tra_count).val(), 10));
                $($.lt_id_data.id_cf_money).html($.lt_total_money_trace);
                $($.lt_id_data.id_cf_count).html(parseInt($($.lt_id_data.id_cf_count).html(), 10) + 1);
                var pc = 0;
                var pz = 0;
                $.each($.lt_method_data.prize,
                    function (i, n) {
                        n = isNaN(Number(n)) ? 0 : Number(n);
                        pz = pz > n ? pz : n;
                        pc++
                    });
                if (pc != 1) {
                    pz = 0
                }
                pz = Math.round(pz * ($.lt_method_data.modes[modes].rate * 1000)) / 1000;
                pz = sel_isdy ? sel_prize : pz;
                var aPositionTile = $.lt_position_title;
                var iPositionLen = current_positionsel.length;
                var positiondesc = "";
                if (iPositionLen > 0) {
                    for (var i = 0; i < iPositionLen; i++) {
                        positiondesc += aPositionTile[current_positionsel[i]];
                        if (i < iPositionLen - 1) {
                            positiondesc += "\u3001"
                        }
                    }
                }
                $cfhtml.data("data", {
                    methodid: mid,
                    methodname: $.lt_method_data.title + "_" + $.lt_method_data.name,
                    nums: nums,
                    money: money,
                    modes: modes,
                    position: cur_position,
                    positiondesc: positiondesc,
                    modename: $.lt_method_data.modes[modes].name,
                    basemoney: basemoney,
                    prize: pz,
                    code: temp.join("|"),
                    desc: nohtml,
                    isrx: $.lt_method_data.isrx
                });
                if ($.lt_same_code[mid] == undefined) {
                    $.lt_same_code[mid] = []
                }
                if ($.lt_same_code[mid][modes] == undefined) {
                    $.lt_same_code[mid][modes] = []
                }
                if ($.lt_same_code[mid][modes][cur_position] == undefined) {
                    $.lt_same_code[mid][modes][cur_position] = []
                }
                $.lt_same_code[mid][modes][cur_position].push(temp.join("|"));
                $(".td07", $cfhtml).attr("title", lot_lang.dec_s24).click(function () {
                    var n = $cfhtml.data("data").nums;
                    var m = $cfhtml.data("data").money;
                    var b = $cfhtml.data("data").basemoney;
                    var c = $cfhtml.data("data").code;
                    var d = $cfhtml.data("data").methodid;
                    var f = $cfhtml.data("data").modes;
                    var p = $cfhtml.data("data").position;
                    var i = null;
                    $.each($.lt_same_code[d][f][p],
                        function (k, code) {
                            if (code == c) {
                                i = k
                            }
                        });
                    if (i != null) {
                        $.lt_same_code[d][f][p].splice(i, 1)
                    } else {
                        $.alert(lot_lang.am_s27);
                        return
                    }
                    $.lt_total_nums -= n;
                    $.lt_total_money -= m;
                    $.lt_total_money = Math.round($.lt_total_money * 1000) / 1000;
                    $.lt_trace_base = Math.round(($.lt_trace_base - b) * 1000) / 1000;
                    $(this).parent().remove();
                    if ($.lt_total_nums == 0) {
                        $($.lt_id_data.id_cf_content).append('<li class="item none">暂无投注项</li>')
                    }
                    $($.lt_id_data.id_cf_num).html($.lt_total_nums);
                    $.lt_total_money_trace = accMul($.lt_total_money, parseInt($($.lt_id_data.lt_tra_count).val(), 10));
                    $($.lt_id_data.id_cf_money).html($.lt_total_money_trace);
                    $($.lt_id_data.id_cf_count).html(parseInt($($.lt_id_data.id_cf_count).html(), 10) - 1);
                    if ($.lt_ismargin == false) {
                        traceCheckMarginSup()
                    }
                    $(this).parent().closeFloat()
                });
                SetCookie("modes", modes, 86400);
                SetCookie("dypoint", sel_point, 86400);
                for (i = 0; i < data_sel.length; i++) {
                    data_sel[i] = []
                }
                if (otype == "input") {
                    $("#lt_write_box", $(me)).val("")
                } else {
                    if (otype == "digital" || otype == "dxds" || otype == "dds" || otype == "digitalts") {
                        $("span,div", $(me)).filter(".on").removeClass("on")
                    }
                }
                $($.lt_id_data.id_sel_times).val(1);
                checkNum();
                if ($.lt_ismargin == true) {
                    traceCheckMarginSup()
                }
            }
        });
        $.fn.extend({
            fastBet: function () {
                var nums = parseInt($($.lt_id_data.id_sel_num).html(), 10);
                var times = parseInt($($.lt_id_data.id_sel_times).val(), 10);
                var modes = parseInt($($.lt_id_data.id_sel_modes).attr("value"), 10);
                var money = Math.round(times * nums * 2 * ($.lt_method_data.modes[modes].rate * 1000)) / 1000;
                var mid = $.lt_method_data.methodid;
                var current_positionsel = $.lt_position_sel;
                var current_methodtitle = $.lt_method_data.title;
                var current_methodname = $.lt_method_data.name;
                if (current_positionsel.length > 0 && $.lt_rxmode == true) {
                    if (current_positionsel.length < $.lt_method_data.numcount) {
                        $.alert(lot_lang.am_s37.replace("%s", $.lt_method_data.numcount).replace("%m", current_methodtitle));
                        return
                    }
                }
                var cur_position = 0;
                if (current_positionsel.length > 0) {
                    $.each(current_positionsel,
                        function (i, n) {
                            cur_position += Math.pow(2, 4 - parseInt(n, 10))
                        })
                }
                if (isNaN(nums) || isNaN(times) || isNaN(money) || money <= 0) {
                    $.alert(otype == "input" ? lot_lang.am_s29 : lot_lang.am_s19);
                    return
                }
                if (otype == "input") {
                    var mname = $.lt_method[mid];
                    var error = [];
                    var edump = [];
                    var ermsg = "";
                    edump = dumpNum(true);
                    if (edump.length > 0) {
                        ermsg += lot_lang.em_s2 + "\n" + edump.join(", ") + "\r&nbsp;";
                        checkNum();
                        nums = parseInt($($.lt_id_data.id_sel_num).html(), 10);
                        money = Math.round(times * nums * 2 * ($.lt_method_data.modes[modes].rate * 1000)) / 1000
                    }
                    switch (mname) {
                        case "ZX5":
                            error = _inputCheck_Num(5, true);
                            break;
                        case "ZX4":
                        case "RZX4":
                            error = _inputCheck_Num(4, true);
                            break;
                        case "ZX3":
                        case "RZX3":
                            error = _inputCheck_Num(3, true);
                            break;
                        case "HHZX":
                            error = _inputCheck_Num(3, true, _HHZXcheck, true);
                            break;
                        case "ZX2":
                        case "RZX2":
                            error = _inputCheck_Num(2, true);
                            break;
                        case "ZU2":
                            error = _inputCheck_Num(2, true, _HHZXcheck, true);
                            break;
                        case "ZUS":
                            error = _inputCheck_Num(3, true, _ZUSDScheck, true);
                            break;
                        case "ZUL":
                            error = _inputCheck_Num(3, true, _ZULDScheck, true);
                            break;
                        default:
                            break
                    }
                    if (error.length > 0) {
                        ermsg += lot_lang.em_s1 + "\n" + error.join(", ") + "\r&nbsp;"
                    }
                    if (ermsg.length > 1) {
                        $.alert("<div class='datainfo'>" + ermsg + "</div>", "", "", 400)
                    }
                }
                var nos = $.lt_method_data.str;
                var serverdata = "{'type':'" + otype + "','methodid':" + mid + ",'codes':'";
                var temp = [];
                for (i = 0; i < data_sel.length; i++) {
                    if (otype == "input") {
                        nos = nos.replace("X", data_sel[i].sort(_SortNum).join("|"))
                    } else {
                        nos = nos.replace("X", data_sel[i].sort(_SortNum).join($.lt_method_data.sp))
                    }
                    temp.push(data_sel[i].sort(_SortNum).join("&"))
                }
                if (nos.length > 40) {
                    var nohtml = nos.substring(0, 35) + "..."
                } else {
                    var nohtml = nos
                }
                if ($.lt_same_code[mid] != undefined && $.lt_same_code[mid][modes] != undefined && $.lt_same_code[mid][modes][cur_position] != undefined && $.lt_same_code[mid][modes][cur_position].length > 0) {
                    if ($.inArray(temp.join("|"), $.lt_same_code[mid][modes][cur_position]) != -1) {
                        $.alert(lot_lang.am_s28);
                        return false
                    }
                }
                var sel_isdy = false;
                var sel_prize = 0;
                var sel_point = 1;
                if ($.lt_method_data.dyprize.length == 1 && $.lt_isdyna == 1) {
                    if ($("#lt_sel_dyprize") == undefined) {
                        $.alert(lot_lang.am_s27);
                        return false
                    }
                    var sel_dy = $("#lt_sel_dyprize").val();
                    sel_dy = sel_dy.split("|");
                    if (sel_dy[1] == undefined) {
                        $.alert(lot_lang.am_s27);
                        return false
                    }
                    sel_isdy = true;
                    sel_prize = Math.round(Number(sel_dy[0]) * ($.lt_method_data.modes[modes].rate * 1000)) / 1000;
                    sel_point = Number(sel_dy[1])
                }
                noshtml = "[" + $.lt_method_data.title + "_" + $.lt_method_data.name + "] " + nohtml;
                if ($.lt_method[mid] == "DXDS") {
                    noshtml = "[" + $.lt_method_data.name + "] " + nohtml
                }
                var myDate = new Date();
                var curTimes = myDate.getTime();
                var code01 = temp.join("|");
                var zipFlag = "0";
                var codesZip = "";
                var zipConFlag = false;
                if (otype == "input") {
                    if (nums > 5000) {
                        zipConFlag = true
                    }
                }
                if (zipConFlag) {
                    var compression_mode = 6,
                        my_lzma = new LZMA("/js/lzma/lzma_worker.js");
                    my_lzma.compress(code01, compression_mode,
                        function (result) {
                            codesZip = convert_to_formated_hex(result);
                            if (current_positionsel.length > 0) {
                                serverdata += codesZip + "','zip':" + 1 + ",'nums':" + nums + ",'times':" + times + ",'money':" + money + ",'mode':" + modes + ",'point':'" + sel_point + "','desc':'" + noshtml + "','position':'" + current_positionsel.join("&") + "','curtimes':'" + curTimes + "'}"
                            } else {
                                serverdata += codesZip + "','zip':" + 1 + ",'nums':" + nums + ",'times':" + times + ",'money':" + money + ",'mode':" + modes + ",'point':'" + sel_point + "','desc':'" + noshtml + "','curtimes':'" + curTimes + "'}"
                            }
                            var _codes = "";
                            if (temp.length == 1) {
                                _codes = temp.join("").replace(/&/g, "|")
                            } else {
                                _codes = temp.join("|").replace(/&/g, "")
                            }
                            var cfhtml = [];
                            cfhtml.push('<li class="item">');
                            cfhtml.push('<span class="td01">' + ($.lt_method_data.title + "_" + $.lt_method_data.name) + "</span>");
                            cfhtml.push('<span class="td02">' + _codes + "</span>");
                            cfhtml.push('<span class="td03">' + $.lt_method_data.modes[modes].name + "</span>");
                            cfhtml.push('<span class="td04">' + nums + "</span>");
                            cfhtml.push('<span class="td05">' + times + "</span>");
                            cfhtml.push('<span class="td06">' + money + lot_lang.dec_s3 + "</span>");
                            cfhtml.push('<span class="td07 last"><img src="images/cathectic/pic12.png"/></span>');
                            cfhtml.push('<input type="hidden" name="lt_project[]" value="' + serverdata + '" /></li>');
                            var $cfhtml = $(cfhtml.join(""));
                            if ($.lt_total_nums == 0) {
                                $($.lt_id_data.id_cf_content).children().filter(".item").remove()
                            }
                            $($.lt_id_data.id_cf_content).find("li.first").after($cfhtml);
                            $(".td01", $cfhtml).parent().click(function () {
                                var aPositionTile = $.lt_position_title;
                                var iPositionLen = current_positionsel.length;
                                var positionname = "";
                                if (iPositionLen > 0) {
                                    positionname += "<br/>" + lot_lang.dec_s40;
                                    for (var i = 0; i < iPositionLen; i++) {
                                        positionname += aPositionTile[current_positionsel[i]];
                                        if (i < iPositionLen - 1) {
                                            positionname += "\u3001"
                                        }
                                    }
                                }
                                var sss = '<h4 style="text-align:left;">' + lot_lang.dec_s30 + ": " + current_methodtitle + "_" + current_methodname + positionname + "<br/>" + lot_lang.dec_s32 + ": " + $.lt_method_data.modes[modes].name + lot_lang.dec_s32 + (sel_isdy ? (", " + lot_lang.dec_s33 + " " + sel_prize + ", " + lot_lang.dec_s34 + " " + (Math.ceil(sel_point * 1000) / 10) + "%") : "") + "<br/>" + lot_lang.dec_s35 + " " + nums + " " + lot_lang.dec_s1 + ", " + times + " " + lot_lang.dec_s2 + ", " + lot_lang.dec_s36 + " " + money + " " + lot_lang.dec_s3;
                                var methodcode = $.lt_method[mid];
                                var tmpcodenos = "";
                                var dataheight = 60;
                                switch (methodcode) {
                                    case "RZX2":
                                    case "RZX3":
                                    case "RZX4":
                                        if (otype == "input") {
                                            tmpcodenos = nos;
                                            sss += "</h4>"
                                        } else {
                                            var aAllCode = nos.split(",");
                                            var iCodeLen = aAllCode.length;
                                            var len = 0;
                                            var aCodePosition = [];
                                            for (i = 0; i < iCodeLen; i++) {
                                                len = aAllCode[i].length;
                                                if (len > 0) {
                                                    aCodePosition.push(i)
                                                }
                                            }
                                            var sellen = methodcode.substring(methodcode.length - 1);
                                            var aPositionCombo = getCombination(aCodePosition, sellen);
                                            var iComboLen = aPositionCombo.length;
                                            dataheight = iComboLen < 5 ? 60 : iComboLen * 15;
                                            var aCombo = [];
                                            var iLen = 0;
                                            for (j = 0; j < iComboLen; j++) {
                                                aCombo = aPositionCombo[j].split(",");
                                                iLen = aCombo.length;
                                                var tmpnum = "";
                                                var tmptitle = "";
                                                var tmpnums = 1;
                                                for (h = 0; h < iLen; h++) {
                                                    tmpnum += aAllCode[aCombo[h]];
                                                    tmpnums *= aAllCode[aCombo[h]].length;
                                                    tmptitle += aPositionTile[aCombo[h]];
                                                    if (h < iLen - 1) {
                                                        tmpnum += ",";
                                                        tmptitle += "\u3001"
                                                    }
                                                }
                                                tmpcodenos += "[" + tmptitle + "]  " + tmpnum + "&nbsp;&nbsp;|&nbsp;&nbsp;" + tmpnums + lot_lang.dec_s1;
                                                if (j < iComboLen - 1) {
                                                    tmpcodenos += "<br>"
                                                }
                                            }
                                            sss += " , " + lot_lang.dec_s36 + iComboLen + lot_lang.dec_s38;
                                            sss += "<br><font color=red>" + lot_lang.dec_s39 + "</font></h4>"
                                        }
                                        break;
                                    default:
                                        sss += "</h4>";
                                        tmpcodenos = nos;
                                        break
                                }
                                sss += '<div class="data" style="height:' + dataheight + 'px;"><table border=0 cellspacing=0 cellpadding=0><tr><td>' + tmpcodenos + "</td></tr></table></div>";
                                $.alert(sss, lot_lang.dec_s5, "", 450, false)
                            });
                            $.lt_total_nums += nums;
                            $.lt_total_money += money;
                            $.lt_total_money = Math.round($.lt_total_money * 1000) / 1000;
                            basemoney = Math.round(nums * 2 * ($.lt_method_data.modes[modes].rate * 1000)) / 1000;
                            $.lt_trace_base = Math.round(($.lt_trace_base + basemoney) * 1000) / 1000;
                            $($.lt_id_data.id_cf_num).html($.lt_total_nums);
                            $.lt_total_money_trace = accMul($.lt_total_money, parseInt($($.lt_id_data.lt_tra_count).val(), 10));
                            $($.lt_id_data.id_cf_money).html($.lt_total_money_trace);
                            $($.lt_id_data.id_cf_count).html(parseInt($($.lt_id_data.id_cf_count).html(), 10) + 1);
                            var pc = 0;
                            var pz = 0;
                            $.each($.lt_method_data.prize,
                                function (i, n) {
                                    n = isNaN(Number(n)) ? 0 : Number(n);
                                    pz = pz > n ? pz : n;
                                    pc++
                                });
                            if (pc != 1) {
                                pz = 0
                            }
                            pz = Math.round(pz * ($.lt_method_data.modes[modes].rate * 1000)) / 1000;
                            pz = sel_isdy ? sel_prize : pz;
                            var aPositionTile = $.lt_position_title;
                            var iPositionLen = current_positionsel.length;
                            var positiondesc = "";
                            if (iPositionLen > 0) {
                                for (var i = 0; i < iPositionLen; i++) {
                                    positiondesc += aPositionTile[current_positionsel[i]];
                                    if (i < iPositionLen - 1) {
                                        positiondesc += "\u3001"
                                    }
                                }
                            }
                            $cfhtml.data("data", {
                                methodid: mid,
                                methodname: $.lt_method_data.title + "_" + $.lt_method_data.name,
                                nums: nums,
                                money: money,
                                modes: modes,
                                position: cur_position,
                                positiondesc: positiondesc,
                                modename: $.lt_method_data.modes[modes].name,
                                basemoney: basemoney,
                                prize: pz,
                                code: temp.join("|"),
                                desc: nohtml,
                                isrx: $.lt_method_data.isrx
                            });
                            if ($.lt_same_code[mid] == undefined) {
                                $.lt_same_code[mid] = []
                            }
                            if ($.lt_same_code[mid][modes] == undefined) {
                                $.lt_same_code[mid][modes] = []
                            }
                            if ($.lt_same_code[mid][modes][cur_position] == undefined) {
                                $.lt_same_code[mid][modes][cur_position] = []
                            }
                            $.lt_same_code[mid][modes][cur_position].push(temp.join("|"));
                            $(".td07", $cfhtml).attr("title", lot_lang.dec_s24).click(function () {
                                var n = $cfhtml.data("data").nums;
                                var m = $cfhtml.data("data").money;
                                var b = $cfhtml.data("data").basemoney;
                                var c = $cfhtml.data("data").code;
                                var d = $cfhtml.data("data").methodid;
                                var f = $cfhtml.data("data").modes;
                                var p = $cfhtml.data("data").position;
                                var i = null;
                                $.each($.lt_same_code[d][f][p],
                                    function (k, code) {
                                        if (code == c) {
                                            i = k
                                        }
                                    });
                                if (i != null) {
                                    $.lt_same_code[d][f][p].splice(i, 1)
                                } else {
                                    $.alert(lot_lang.am_s27);
                                    return
                                }
                                $.lt_total_nums -= n;
                                $.lt_total_money -= m;
                                $.lt_total_money = Math.round($.lt_total_money * 1000) / 1000;
                                $.lt_trace_base = Math.round(($.lt_trace_base - b) * 1000) / 1000;
                                $(this).parent().remove();
                                if ($.lt_total_nums == 0) {
                                    $($.lt_id_data.id_cf_content).append('<li class="item none">暂无投注项</li>')
                                }
                                $($.lt_id_data.id_cf_num).html($.lt_total_nums);
                                $.lt_total_money_trace = accMul($.lt_total_money, parseInt($($.lt_id_data.lt_tra_count).val(), 10));
                                $($.lt_id_data.id_cf_money).html($.lt_total_money_trace);
                                $($.lt_id_data.id_cf_count).html(parseInt($($.lt_id_data.id_cf_count).html(), 10) - 1);
                                if ($.lt_ismargin == false) {
                                    traceCheckMarginSup()
                                }
                                $(this).parent().closeFloat()
                            });
                            SetCookie("modes", modes, 86400);
                            SetCookie("dypoint", sel_point, 86400);
                            for (i = 0; i < data_sel.length; i++) {
                                data_sel[i] = []
                            }
                            if (otype == "input") {
                                $("#lt_write_box", $(me)).val("")
                            } else {
                                if (otype == "digital" || otype == "dxds" || otype == "dds" || otype == "digitalts") {
                                    $("span,div", $(me)).filter(".on").removeClass("on")
                                }
                            }
                            $($.lt_id_data.id_sel_times).val(1);
                            checkNum();
                            if ($.lt_ismargin == true) {
                                traceCheckMarginSup()
                            }
                            if ($(".layui-layer").length == 0 && $.lt_total_nums && $.lt_total_money) {
                                $("#lt_total_nums").val($.lt_total_nums);
                                $("#lt_total_money").val($.lt_total_money);
                                $("#tk02").show();
                                $("#td02_money").html($.lt_total_money_trace);
                                $("#tk02_content").html($("#lt_cf_content").html());
                                $(".reveal-modal-close").on("click",
                                    function () {
                                        $(this).parents(".reveal-modal").parent().hide()
                                    });
                                $("#tk02 .reveal-modal-submit").unbind("click").on("click",
                                    function () {
                                        $(this).parents(".reveal-modal").parent().hide();
                                        ajaxSubmit(me)
                                    })
                            }
                        })
                } else {
                    if (current_positionsel.length > 0) {
                        serverdata += temp.join("|") + "','zip':" + 0 + ",'nums':" + nums + ",'times':" + times + ",'money':" + money + ",'mode':" + modes + ",'point':'" + sel_point + "','desc':'" + noshtml + "','position':'" + current_positionsel.join("&") + "','curtimes':'" + curTimes + "'}"
                    } else {
                        serverdata += temp.join("|") + "','zip':" + 0 + ",'nums':" + nums + ",'times':" + times + ",'money':" + money + ",'mode':" + modes + ",'point':'" + sel_point + "','desc':'" + noshtml + "','curtimes':'" + curTimes + "'}"
                    }
                    var _codes = "";
                    if (temp.length == 1) {
                        _codes = temp.join("").replace(/&/g, "|")
                    } else {
                        _codes = temp.join("|").replace(/&/g, "")
                    }
                    var cfhtml = [];
                    cfhtml.push('<li class="item">');
                    cfhtml.push('<span class="td01">' + ($.lt_method_data.title + "_" + $.lt_method_data.name) + "</span>");
                    cfhtml.push('<span class="td02">' + _codes + "</span>");
                    cfhtml.push('<span class="td03">' + $.lt_method_data.modes[modes].name + "</span>");
                    cfhtml.push('<span class="td04">' + nums + "</span>");
                    cfhtml.push('<span class="td05">' + times + "</span>");
                    cfhtml.push('<span class="td06">' + money + lot_lang.dec_s3 + "</span>");
                    cfhtml.push('<span class="td07 last"><img src="images/cathectic/pic12.png"/></span>');
                    cfhtml.push('<input type="hidden" name="lt_project[]" value="' + serverdata + '" /></li>');
                    var $cfhtml = $(cfhtml.join(""));
                    if ($.lt_total_nums == 0) {
                        $($.lt_id_data.id_cf_content).children().filter(".item").remove()
                    }
                    $($.lt_id_data.id_cf_content).find("li.first").after($cfhtml);
                    $(".td01", $cfhtml).parent().click(function () {
                        var aPositionTile = $.lt_position_title;
                        var iPositionLen = current_positionsel.length;
                        var positionname = "";
                        if (iPositionLen > 0) {
                            positionname += "<br/>" + lot_lang.dec_s40;
                            for (var i = 0; i < iPositionLen; i++) {
                                positionname += aPositionTile[current_positionsel[i]];
                                if (i < iPositionLen - 1) {
                                    positionname += "\u3001"
                                }
                            }
                        }
                        var sss = '<h4 style="text-align:left;">' + lot_lang.dec_s30 + ": " + current_methodtitle + "_" + current_methodname + positionname + "<br/>" + lot_lang.dec_s32 + ": " + $.lt_method_data.modes[modes].name + lot_lang.dec_s32 + (sel_isdy ? (", " + lot_lang.dec_s33 + " " + sel_prize + ", " + lot_lang.dec_s34 + " " + (Math.ceil(sel_point * 1000) / 10) + "%") : "") + "<br/>" + lot_lang.dec_s35 + " " + nums + " " + lot_lang.dec_s1 + ", " + times + " " + lot_lang.dec_s2 + ", " + lot_lang.dec_s36 + " " + money + " " + lot_lang.dec_s3;
                        var methodcode = $.lt_method[mid];
                        var tmpcodenos = "";
                        var dataheight = 60;
                        switch (methodcode) {
                            case "RZX2":
                            case "RZX3":
                            case "RZX4":
                                if (otype == "input") {
                                    tmpcodenos = nos;
                                    sss += "</h4>"
                                } else {
                                    var aAllCode = nos.split(",");
                                    var iCodeLen = aAllCode.length;
                                    var len = 0;
                                    var aCodePosition = [];
                                    for (i = 0; i < iCodeLen; i++) {
                                        len = aAllCode[i].length;
                                        if (len > 0) {
                                            aCodePosition.push(i)
                                        }
                                    }
                                    var sellen = methodcode.substring(methodcode.length - 1);
                                    var aPositionCombo = getCombination(aCodePosition, sellen);
                                    var iComboLen = aPositionCombo.length;
                                    dataheight = iComboLen < 5 ? 60 : iComboLen * 15;
                                    var aCombo = [];
                                    var iLen = 0;
                                    for (j = 0; j < iComboLen; j++) {
                                        aCombo = aPositionCombo[j].split(",");
                                        iLen = aCombo.length;
                                        var tmpnum = "";
                                        var tmptitle = "";
                                        var tmpnums = 1;
                                        for (h = 0; h < iLen; h++) {
                                            tmpnum += aAllCode[aCombo[h]];
                                            tmpnums *= aAllCode[aCombo[h]].length;
                                            tmptitle += aPositionTile[aCombo[h]];
                                            if (h < iLen - 1) {
                                                tmpnum += ",";
                                                tmptitle += "\u3001"
                                            }
                                        }
                                        tmpcodenos += "[" + tmptitle + "]  " + tmpnum + "&nbsp;&nbsp;|&nbsp;&nbsp;" + tmpnums + lot_lang.dec_s1;
                                        if (j < iComboLen - 1) {
                                            tmpcodenos += "<br>"
                                        }
                                    }
                                    sss += " , " + lot_lang.dec_s36 + iComboLen + lot_lang.dec_s38;
                                    sss += "<br><font color=red>" + lot_lang.dec_s39 + "</font></h4>"
                                }
                                break;
                            default:
                                sss += "</h4>";
                                tmpcodenos = nos;
                                break
                        }
                        sss += '<div class="data" style="height:' + dataheight + 'px;"><table border=0 cellspacing=0 cellpadding=0><tr><td>' + tmpcodenos + "</td></tr></table></div>";
                        $.alert(sss, lot_lang.dec_s5, "", 450, false)
                    });
                    $.lt_total_nums += nums;
                    $.lt_total_money += money;
                    $.lt_total_money = Math.round($.lt_total_money * 1000) / 1000;
                    basemoney = Math.round(nums * 2 * ($.lt_method_data.modes[modes].rate * 1000)) / 1000;
                    $.lt_trace_base = Math.round(($.lt_trace_base + basemoney) * 1000) / 1000;
                    $($.lt_id_data.id_cf_num).html($.lt_total_nums);
                    $.lt_total_money_trace = accMul($.lt_total_money, parseInt($($.lt_id_data.lt_tra_count).val(), 10));
                    $($.lt_id_data.id_cf_money).html($.lt_total_money_trace);
                    $($.lt_id_data.id_cf_count).html(parseInt($($.lt_id_data.id_cf_count).html(), 10) + 1);
                    var pc = 0;
                    var pz = 0;
                    $.each($.lt_method_data.prize,
                        function (i, n) {
                            n = isNaN(Number(n)) ? 0 : Number(n);
                            pz = pz > n ? pz : n;
                            pc++
                        });
                    if (pc != 1) {
                        pz = 0
                    }
                    pz = Math.round(pz * ($.lt_method_data.modes[modes].rate * 1000)) / 1000;
                    pz = sel_isdy ? sel_prize : pz;
                    var aPositionTile = $.lt_position_title;
                    var iPositionLen = current_positionsel.length;
                    var positiondesc = "";
                    if (iPositionLen > 0) {
                        for (var i = 0; i < iPositionLen; i++) {
                            positiondesc += aPositionTile[current_positionsel[i]];
                            if (i < iPositionLen - 1) {
                                positiondesc += "\u3001"
                            }
                        }
                    }
                    $cfhtml.data("data", {
                        methodid: mid,
                        methodname: $.lt_method_data.title + "_" + $.lt_method_data.name,
                        nums: nums,
                        money: money,
                        modes: modes,
                        position: cur_position,
                        positiondesc: positiondesc,
                        modename: $.lt_method_data.modes[modes].name,
                        basemoney: basemoney,
                        prize: pz,
                        code: temp.join("|"),
                        desc: nohtml,
                        isrx: $.lt_method_data.isrx
                    });
                    if ($.lt_same_code[mid] == undefined) {
                        $.lt_same_code[mid] = []
                    }
                    if ($.lt_same_code[mid][modes] == undefined) {
                        $.lt_same_code[mid][modes] = []
                    }
                    if ($.lt_same_code[mid][modes][cur_position] == undefined) {
                        $.lt_same_code[mid][modes][cur_position] = []
                    }
                    $.lt_same_code[mid][modes][cur_position].push(temp.join("|"));
                    $(".td07", $cfhtml).attr("title", lot_lang.dec_s24).click(function () {
                        var n = $cfhtml.data("data").nums;
                        var m = $cfhtml.data("data").money;
                        var b = $cfhtml.data("data").basemoney;
                        var c = $cfhtml.data("data").code;
                        var d = $cfhtml.data("data").methodid;
                        var f = $cfhtml.data("data").modes;
                        var p = $cfhtml.data("data").position;
                        var i = null;
                        $.each($.lt_same_code[d][f][p],
                            function (k, code) {
                                if (code == c) {
                                    i = k
                                }
                            });
                        if (i != null) {
                            $.lt_same_code[d][f][p].splice(i, 1)
                        } else {
                            $.alert(lot_lang.am_s27);
                            return
                        }
                        $.lt_total_nums -= n;
                        $.lt_total_money -= m;
                        $.lt_total_money = Math.round($.lt_total_money * 1000) / 1000;
                        $.lt_trace_base = Math.round(($.lt_trace_base - b) * 1000) / 1000;
                        $(this).parent().remove();
                        if ($.lt_total_nums == 0) {
                            $($.lt_id_data.id_cf_content).append('<li class="item none">暂无投注项</li>')
                        }
                        $($.lt_id_data.id_cf_num).html($.lt_total_nums);
                        $.lt_total_money_trace = accMul($.lt_total_money, parseInt($($.lt_id_data.lt_tra_count).val(), 10));
                        $($.lt_id_data.id_cf_money).html($.lt_total_money_trace);
                        $($.lt_id_data.id_cf_count).html(parseInt($($.lt_id_data.id_cf_count).html(), 10) - 1);
                        if ($.lt_ismargin == false) {
                            traceCheckMarginSup()
                        }
                        $(this).parent().closeFloat()
                    });
                    SetCookie("modes", modes, 86400);
                    SetCookie("dypoint", sel_point, 86400);
                    for (i = 0; i < data_sel.length; i++) {
                        data_sel[i] = []
                    }
                    if (otype == "input") {
                        $("#lt_write_box", $(me)).val("")
                    } else {
                        if (otype == "digital" || otype == "dxds" || otype == "dds" || otype == "digitalts") {
                            $("span,div", $(me)).filter(".on").removeClass("on")
                        }
                    }
                    $($.lt_id_data.id_sel_times).val(1);
                    checkNum();
                    if ($.lt_ismargin == true) {
                        traceCheckMarginSup()
                    }
                    if ($(".layui-layer").length == 0 && $.lt_total_nums && $.lt_total_money) {
                        $("#lt_total_nums").val($.lt_total_nums);
                        $("#lt_total_money").val($.lt_total_money);
                        $("#tk02").show();
                        $("#td02_money").html($.lt_total_money_trace);
                        $("#tk02_content").html($("#lt_cf_content").html());
                        $(".reveal-modal-close").on("click",
                            function () {
                                $(this).parents(".reveal-modal").parent().hide()
                            });
                        $("#tk02 .reveal-modal-submit").unbind("click").on("click",
                            function () {
                                $(this).parents(".reveal-modal").parent().hide();
                                ajaxSubmit(me)
                            })
                    }
                }
            }
        })
    };
    var traceCheckMarginSup = function () {
        var marmt = 0;
        var marmd = 0;
        var martype = 0;
        var p = 0;
        if ($.lt_total_nums > 0) {
            $.each($("li.item", $($.lt_id_data.id_cf_content)),
                function (i, n) {
                    if (marmt != 0 && marmt != $(n).data("data").methodid) {
                        martype = 2;
                        return false
                    } else {
                        marmt = $(n).data("data").methodid
                    }
                    if (marmd != 0 && marmd != $(n).data("data").modes) {
                        martype = 3;
                        return false
                    } else {
                        marmd = $(n).data("data").modes
                    }
                    if ($(n).data("data").prize <= 0 || (p != 0 && p != $(n).data("data").prize) || $(n).data("data").isrx == 1) {
                        martype = 1;
                        return false
                    } else {
                        p = $(n).data("data").prize
                    }
                })
        }
        if (martype > 0) {
            $.lt_ismargin = false;
            $("#lt_margin").hide();
            $("#lt_margin_html").hide();
            $("#lt_sametime").click()
        } else {
            $.lt_ismargin = true;
            $("#lt_margin").show();
            $("#lt_margin_html").show();
            $("#lt_margin").click()
        }
        return true
    };
    $.lt_reset = function (iskeep) {
        if (iskeep && iskeep === true) {
            iskeep = true
        } else {
            iskeep = false
        }
        if ($.lt_time_leave <= 0) {
            if (iskeep == false) {
                $("span[id^='smalllabel_'][class='method-tab-front']", $($.lt_id_data.id_smalllabel)).removeData("ischecked").click()
            }
            if (iskeep == false) {
                $.lt_total_nums = 0;
                $.lt_total_money = 0;
                $.lt_trace_base = 0;
                $.lt_same_code = [];
                $($.lt_id_data.id_cf_num).html(0);
                $($.lt_id_data.id_cf_money).html(0);
                $($.lt_id_data.id_cf_content).children().filter(".item").remove();
                $($.lt_id_data.id_cf_content).append('<li class="item none">暂无投注项</li>');
                $($.lt_id_data.id_cf_count).html(0);
                if ($.lt_ismargin == false) {
                    traceCheckMarginSup()
                }
            }
        } else {
            if (iskeep == false) {
                $("span[id^='smalllabel_'][class='method-tab-front']", $($.lt_id_data.id_smalllabel)).removeData("ischecked").click()
            }
            if (iskeep == false) {
                $.lt_total_nums = 0;
                $.lt_total_money = 0;
                $.lt_trace_base = 0;
                $.lt_same_code = [];
                $($.lt_id_data.id_cf_num).html(0);
                $($.lt_id_data.id_cf_money).html(0);
                $($.lt_id_data.id_cf_content).children().filter(".item").remove();
                $($.lt_id_data.id_cf_content).append('<li class="item none">暂无投注项</li>');
                $($.lt_id_data.id_cf_count).html(0);
                if ($.lt_ismargin == false) {
                    traceCheckMarginSup()
                }
            }
        }
    };
    $.fn.alertSubmit = function () {
        var me = this;
        $(this).on("click",
            function () {
                if (checkTimeOut() == false) {
                    return
                }
                $.lt_submiting = true;
                if ($.lt_total_nums <= 0 || $.lt_total_money <= 0) {
                    $.lt_submiting = false;
                    $.alert(lot_lang.am_s7);
                    return
                }
                $("#lt_total_nums").val($.lt_total_nums);
                $("#lt_total_money").val($.lt_total_money);
                $("#tk02").show();
                $("#td02_money").html($.lt_total_money_trace);
                $("#tk02_content").html($("#lt_cf_content").html());
                $(".reveal-modal-close").on("click",
                    function () {
                        $(this).parents(".reveal-modal").parent().hide()
                    });
                $("#tk02 .reveal-modal-submit").unbind("click").on("click",
                    function () {
                        $(this).parents(".reveal-modal").parent().hide();
                        ajaxSubmit(me)
                    })
            })
    };
    $.fn.lt_ajaxSubmit = function () {
        var me = this;
        $(this).click(function () {
            $.lt_submiting = true;
            var istrace = false;
            if ($.lt_total_nums <= 0 || $.lt_total_money <= 0) {
                $.lt_submiting = false;
                $.alert(lot_lang.am_s7);
                return
            }
            var msg = "<h4>" + lot_lang.dec_s81 + "</h4>";
            msg += '<div class="data"><table border=0 cellspacing=0 cellpadding=0 width=100%><tr class=hid><td width=135></td><td width=20></td><td width=180></td><td></td></tr>';
            var modesmsg = [];
            var modes = 0;
            $.each($("li.item", $($.lt_id_data.id_cf_content)),
                function (i, n) {
                    datas = $(n).data("data");
                    if (datas == undefined) {
                        return
                    }
                    if (datas.positiondesc == "") {
                        msg += "<tr><td>" + datas.methodname + "</td><td>" + datas.modename + '</td><td colspan="2">' + datas.desc + "</td></tr>"
                    } else {
                        msg += "<tr><td>" + datas.methodname + "</td><td>" + datas.modename + "</td><td>" + datas.desc + "</td><td>" + datas.positiondesc + "</td></tr>"
                    }
                });
            msg += "</table></div>";
            btmsg = '<div class="binfo"><span class=bbl></span><span class=bbm>' + (istrace == true ? lot_lang.dec_s16 + ": " + JsRound($.lt_trace_money, 2, true) : lot_lang.dec_s9 + ": " + $.lt_total_money_trace) + " " + lot_lang.dec_s3 + "</span><span class=bbr></span></div>";
            if ($(".layui-layer").length == 0 && $.lt_total_nums && $.lt_total_money) {
                $("#lt_total_nums").val($.lt_total_nums);
                $("#lt_total_money").val($.lt_total_money);
                $("#tk02int_con_id").html(lot_lang.am_s144.replace("[count]", $.lt_trace_issue));
                $("#tk02").show();
                $("#td02_money").html($("#lt_trace_hmoney").html());
                $("#tk02_content").html($("#lt_cf_content").html());
                $(".reveal-modal-close").on("click",
                    function () {
                        $(this).parents(".reveal-modal").parent().hide()
                    });
                $("#tk02 .reveal-modal-submit").unbind("click").on("click",
                    function () {
                        $(this).parents(".reveal-modal").parent().hide();
                        ajaxSubmit(me)
                    })
            }
        })
    };
    function checkTimeOut() {
        return true
    }

    $.fn.fastSubmit = function () {
        var me = this;
        $(this).click(function () {
            $.fn.fastBet()
        })
    };
    function ajaxSubmit(me) {
        var modeFlag = true;
        
        if (modeFlag == false) {
            $.alert(lot_lang.am_s101, "",
                function () {
                    if (checkTimeOut() == true) {
                        $.lt_reset()
                    }
                    $.lt_onfinishbuy();
                    $.fn.fastData()
                });
            return
        } else {
            $.blockUI({
                message: lot_lang.am_s22,
                overlayCSS: {
                    backgroundColor: "#000000",
                    opacity: 0.3,
                    cursor: "wait"
                }
            })
        }
        var form = $(me).parents("#mainForm").eq(0);
        $.ajax({
            type: "POST",
            url: $.lt_ajaxurl,
            timeout: 30000,
            contentType: "application/x-www-form-urlencoded;charset=utf-8",
            data: $(form).serialize(),
            success: function (data) {
                $.unblockUI({
                    fadeInTime: 0,
                    fadeOutTime: 0
                });
                $.lt_submiting = false;
                if (data.length <= 0) {
                    $.alert(lot_lang.am_s16);
                    return false
                }
                var partn = /<script.*>.*<\/script>/;
                if (partn.test(data)) {
                    alert(lot_lang.am_s17, "", "", 300);
                    top.location.href = "./main.shtml";
                    return false
                }
                if (data.trim() == "success") {
                    $.alert(lot_lang.am_s24, lot_lang.dec_s25,
                        function () {
                            if (checkTimeOut() == true) {
                                $.lt_reset()
                            }
                            $.lt_onfinishbuy();
                            $.fn.fastData();
                            $.fn.updateWinnumber()
                        });
                    var istrace = $($.lt_id_data.id_tra_if).prop("checked");
                    if (istrace == true) {
                        $($.lt_id_data.id_tra_if).prop("checked", false)
                    }
                    $("#ifNewBet").attr("src", "page/NewBet.shtml?id=" + $.lt_lottid + "&flag=" + Math.random());
                    return false
                } else {
                    eval("data =" + data);
                    if (data.stats == "error") {
                        $.alert(lot_lang.am_s100 + data.data, "",
                            function () {
                                return checkTimeOut()
                            },
                            (data.data.length > 10 ? 350 : 250));
                        return false
                    }
                    if (data.stats == "fail") {
                        msg = "<h4>" + lot_lang.am_s26 + "</h4>";
                        msg += '<div class="data"><table width="100%" border="0" cellspacing="0" cellpadding="0">';
                        $.each(data.data.content,
                            function (i, n) {
                                msg += "<tr><td>" + n.desc + '</td><td width="30%">' + n.errmsg + "</td></tr>"
                            });
                        msg += "</table></div>";
                        btmsg = '<div class="binfo"><span class=bbl></span><span class=bbm>' + lot_lang.am_s25.replace("[success]", data.data.success).replace("[fail]", data.data.fail) + "</span><span class=bbr></span></div>";
                        $.confirm(msg,
                            function () {
                                if (checkTimeOut() == true) {
                                    $.lt_reset()
                                }
                                $.lt_onfinishbuy();
                                $.fn.fastData()
                            },
                            function () {
                                $.lt_onfinishbuy();
                                $.fn.fastData();
                                return checkTimeOut()
                            },
                            "", 500, true, btmsg)
                    }
                }
            },
            error: function () {
                $.lt_submiting = false;
                $.unblockUI({
                    fadeInTime: 0,
                    fadeOutTime: 0
                });
                $.confirm(lot_lang.am_s99.replace("[msg]", lot_lang.am_s23),
                    function () {
                        if (checkTimeOut() == true) {
                            $.lt_reset()
                        }
                        $.lt_onfinishbuy();
                        $.fn.fastData()
                    },
                    function () {
                        $.lt_onfinishbuy();
                        $.fn.fastData();
                        return checkTimeOut()
                    },
                    "", 480, true);
                return false
            }
        })
    }

    $.fn.fastData = function () {
        if (typeof(window.top.ebalance) != "object") {
            return true
        }
        var $lf = $("#ebalance", window.top.document);
        $.ajax({
            type: "POST",
            url: $.lt_ajaxurl,
            data: "flag=balance",
            timeout: 9000,
            success: function (data) {
                $lf.html("￥" + data);
                return true
            }
        })
    };
    $.openMMC = function (numbers) {
        var codebox = $("#showcodebox").find("span");
        $.each(codebox,
            function (i, n) {
                $(this).html("<img src='/images/mv.gif' width='43' />")
            });
        if (numbers == "") {
            numbers = "--,--,--,--,--"
        }
        var arr = numbers.split(",");
        var $len = arr.length;
        var $i = 0;
        if (arr.length != 5) {
            return
        }
        var opencodeno = window.setInterval(function () {
                if ($i >= ($len - 1)) {
                    clearInterval(opencodeno)
                }
                if (arr[$i] != "x") {
                    $(codebox[$i]).html(arr[$i])
                }
                $i++
            },
            500)
    };
    $.fn.updateWinnumber = function () {
        if (parseInt($.lt_showrecord, 10) == 0) {
            return true
        }
        $.ajax({
            type: "POST",
            url: $.lt_ajaxurl,
            data: "lotteryid=" + $.lt_lottid + "&flag=mmcwnum",
            success: function (data) {
                if (data.length <= 0) {
                    $.alert(lot_lang.am_s16);
                    return false
                }
                var partn = /<script.*>.*<\/script>/;
                if (partn.test(data)) {
                    $.alert(lot_lang.am_s16);
                    return false
                }
                eval("data=" + data + ";");
                $("#ewinnumber").empty();
                var $shtml = "";
                var firnum = "";
                var fsk = 0;
                $.each(data,
                    function (i, n) {
                        if (i == 0) {
                            firnum = n.winnumber
                        }
                        fsk++;
                        $shtml += "<tr>";
                        $shtml += "<td></td><td>" + n.winnumber + "</td>";
                        $shtml += "</tr>"
                    });
                if (fsk < 5) {
                    for (i = 0; i < 5 - fsk; i++) {
                        $shtml += "<tr>";
                        $shtml += "<td>-</td><td>-</td>";
                        $shtml += "</tr>"
                    }
                }
                $("#ewinnumber").html($shtml);
                $.openMMC(firnum)
            },
            error: function () {
                $.alert(lot_lang.am_s16)
            }
        })
    }
})(jQuery);