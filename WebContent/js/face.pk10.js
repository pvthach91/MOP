var face = [{
    isrx: "0",
    isdefault: "1",
    title: "定位胆",
    label: [{
        gtitle: "定位胆",
        label: [{
            selectarea: {
                type: "digital",
                layout: [{
                    title: "冠军",
                    no: "01|02|03|04|05|06|07|08|09|10",
                    place: 0,
                    cols: 1
                }, {
                    title: "亚军",
                    no: "01|02|03|04|05|06|07|08|09|10",
                    place: 1,
                    cols: 1
                }, {
                    title: "季军",
                    no: "01|02|03|04|05|06|07|08|09|10",
                    place: 2,
                    cols: 1
                }, {
                    title: "第四名",
                    no: "01|02|03|04|05|06|07|08|09|10",
                    place: 3,
                    cols: 1
                }, {
                    title: "第五名",
                    no: "01|02|03|04|05|06|07|08|09|10",
                    place: 4,
                    cols: 1
                }, {
                    title: "第六名",
                    no: "01|02|03|04|05|06|07|08|09|10",
                    place: 5,
                    cols: 1
                }, {
                    title: "第七名",
                    no: "01|02|03|04|05|06|07|08|09|10",
                    place: 6,
                    cols: 1
                }, {
                    title: "第八名",
                    no: "01|02|03|04|05|06|07|08|09|10",
                    place: 7,
                    cols: 1
                }, {
                    title: "第九名",
                    no: "01|02|03|04|05|06|07|08|09|10",
                    place: 8,
                    cols: 1
                }, {
                    title: "第十名",
                    no: "01|02|03|04|05|06|07|08|09|10",
                    place: 9,
                    cols: 1
                }],
                noBigIndex: 5,
                isButton: true
            },
            show_str: "X,X,X,X,X,X,X,X,X,X",
            code_sp: "s",
            methodid: 1215,
            name: "定位胆",
            methoddesc: "从冠、亚、季、四、五、六、七、八、九、十任意位置上任意选择1个或1个以上号码。",
            methodhelp: "从第1名到第10名任意位置上，任意选择1个或1个以上号码，进行投注，所选车号的排名和开奖结果一致，即为中奖，反之不中奖。",
            methodexample: "投注方案：冠 选择01<br>开奖号码的第一个号码为01，即为中奖。",
            prize: {
                1: "1700.00"
            },
            dyprize: [],
            modes: [{
                modeid: 1,
                name: "元",
                rate: 1
            }, {
                modeid: 2,
                name: "角",
                rate: 0.1
            }, {
                modeid: 3,
                name: "分",
                rate: 0.01
            }],
            maxcodecount: 0,
            isrx: 0,
            numcount: 0,
            defaultposition: "00000",
            desc: "定位胆"
        }]
    }]
}, {
    isrx: "0",
    isdefault: "0",
    title: "精确前四",
    label: [{
        gtitle: "直选",
        label: [{
            selectarea: {
                type: "digital",
                layout: [{
                    title: "冠军",
                    no: "01|02|03|04|05|06|07|08|09|10",
                    place: 0,
                    cols: 1
                }, {
                    title: "亚军",
                    no: "01|02|03|04|05|06|07|08|09|10",
                    place: 1,
                    cols: 1
                }, {
                    title: "季军",
                    no: "01|02|03|04|05|06|07|08|09|10",
                    place: 2,
                    cols: 1
                }, {
                    title: "第四名",
                    no: "01|02|03|04|05|06|07|08|09|10",
                    place: 3,
                    cols: 1
                }],
                noBigIndex: 5,
                isButton: true
            },
            show_str: "X,X,X,X",
            code_sp: "",
            methodid: 1219,
            name: "直选复式",
            methoddesc: "从各名次中各选择1个不重复的号码组成一注。",
            methodhelp: "从第1名、第2名、第3名、第4名中至少各选1个号码组成一注，所选车号的排名和开奖结果一致，即为中奖，反之不中奖。",
            methodexample: "投注方案：1234<br>开奖号码：1234*，即中精准前四。",
            prize: {
                1: "1700.00"
            },
            dyprize: [],
            modes: [{
                modeid: 1,
                name: "元",
                rate: 1
            }, {
                modeid: 2,
                name: "角",
                rate: 0.1
            }, {
                modeid: 3,
                name: "分",
                rate: 0.01
            }, {
                modeid: 4,
                name: "厘",
                rate: 0.001
            }],
            maxcodecount: 0,
            isrx: 0,
            numcount: 0,
            defaultposition: "00000",
            desc: "直选复式"
        }, {
            selectarea: {
                type: "input",
                singletypetips: '三码 01 02 03;02 03 04任选 01 02 03 04 05'
            },
            show_str: "X",
            code_sp: ";",
            methodid: 1219,
            name: "直选单式",
            methoddesc: "手动输入号码，输入4个号码组成一注。",
            methodhelp: "从第1名、第2名、第3名、第4名中至少各选1个号码组成一注，所选车号的排名和开奖结果一致，即为中奖，反之不中奖。",
            methodexample: "手动输入01 02 03 04<br>开奖号码的前4个号码顺序为01 02 03 04，即为中奖。",
            prize: {
                1: "1700.00"
            },
            dyprize: [],
            modes: [{
                modeid: 1,
                name: "元",
                rate: 1
            }, {
                modeid: 2,
                name: "角",
                rate: 0.1
            }, {
                modeid: 3,
                name: "分",
                rate: 0.01
            }, {
                modeid: 4,
                name: "厘",
                rate: 0.001
            }],
            maxcodecount: 0,
            isrx: 0,
            numcount: 0,
            defaultposition: "0000",
            desc: "直选单式"
        }]
    }]
}, {
    isrx: "0",
    isdefault: "0",
    title: "精确前三",
    label: [{
        gtitle: "直选",
        label: [{
            selectarea: {
                type: "digital",
                layout: [{
                    title: "冠军",
                    no: "01|02|03|04|05|06|07|08|09|10",
                    place: 0,
                    cols: 1
                }, {
                    title: "亚军",
                    no: "01|02|03|04|05|06|07|08|09|10",
                    place: 1,
                    cols: 1
                }, {
                    title: "季军",
                    no: "01|02|03|04|05|06|07|08|09|10",
                    place: 2,
                    cols: 1
                }],
                noBigIndex: 5,
                isButton: true
            },
            show_str: "X,X,X",
            code_sp: "",
            methodid: 1218,
            name: "直选复式",
            methoddesc: "从各名次中各选择1个不重复的号码组成一注。",
            methodhelp: "从第1名、第2名、第3名中至少各选1个号码组成一注，所选车号的排名和开奖结果一致，即为中奖，反之不中奖。",
            methodexample: "投注方案：123<br>开奖号码：123*，即中精准前三。",
            prize: {
                1: "1700.00"
            },
            dyprize: [],
            modes: [{
                modeid: 1,
                name: "元",
                rate: 1
            }, {
                modeid: 2,
                name: "角",
                rate: 0.1
            }, {
                modeid: 3,
                name: "分",
                rate: 0.01
            }, {
                modeid: 4,
                name: "厘",
                rate: 0.001
            }],
            maxcodecount: 0,
            isrx: 0,
            numcount: 0,
            defaultposition: "00000",
            desc: "直选复式"
        }, {
            selectarea: {
                type: "input"
            },
            show_str: "X",
            code_sp: " ",
            methodid: 1218,
            name: "直选单式",
            methoddesc: "手动输入号码，输入3个号码组成一注。",
            methodhelp: "从第1名、第2名、第3名中至少各选1个号码组成一注，所选车号的排名和开奖结果一致，即为中奖，反之不中奖。",
            methodexample: "手动输入01 02 03<br>开奖号码的前4个号码顺序为01 02 03，即为中奖。",
            prize: {
                1: "1700.00"
            },
            dyprize: [],
            modes: [{
                modeid: 1,
                name: "元",
                rate: 1
            }, {
                modeid: 2,
                name: "角",
                rate: 0.1
            }, {
                modeid: 3,
                name: "分",
                rate: 0.01
            }, {
                modeid: 4,
                name: "厘",
                rate: 0.001
            }],
            maxcodecount: 0,
            isrx: 0,
            numcount: 0,
            defaultposition: "000",
            desc: "直选单式"
        }]
    }]
}, {
    isrx: "0",
    isdefault: "0",
    title: "精确前二",
    label: [{
        gtitle: "直选",
        label: [{
            selectarea: {
                type: "digital",
                layout: [{
                    title: "冠军",
                    no: "01|02|03|04|05|06|07|08|09|10",
                    place: 0,
                    cols: 1
                }, {
                    title: "亚军",
                    no: "01|02|03|04|05|06|07|08|09|10",
                    place: 1,
                    cols: 1
                }],
                noBigIndex: 5,
                isButton: true
            },
            show_str: "X,X",
            code_sp: "",
            methodid: 1217,
            name: "直选复式",
            methoddesc: "从各名次中各选择1个不重复的号码组成一注。",
            methodhelp: "从第1名、第2名中至少各选1个号码组成一注，所选车号的排名和开奖结果一致，即为中奖，反之不中奖。",
            methodexample: "投注方案：12<br>开奖号码：12*，即中精准前二。",
            prize: {
                1: "1700.00"
            },
            dyprize: [],
            modes: [{
                modeid: 1,
                name: "元",
                rate: 1
            }, {
                modeid: 2,
                name: "角",
                rate: 0.1
            }, {
                modeid: 3,
                name: "分",
                rate: 0.01
            }, {
                modeid: 4,
                name: "厘",
                rate: 0.001
            }],
            maxcodecount: 0,
            isrx: 0,
            numcount: 0,
            defaultposition: "00",
            desc: "直选复式"
        }, {
            selectarea: {
                type: "input"
            },
            show_str: "X",
            code_sp: " ",
            methodid: 1217,
            name: "直选单式",
            methoddesc: "手动输入号码，输入2个号码组成一注。",
            methodhelp: "从第1名、第2名中至少各选1个号码组成一注，所选车号的排名和开奖结果一致，即为中奖，反之不中奖。",
            methodexample: "手动输入01 02<br>开奖号码的前2个号码顺序为01 02，即为中奖。",
            prize: {
                1: "1700.00"
            },
            dyprize: [],
            modes: [{
                modeid: 1,
                name: "元",
                rate: 1
            }, {
                modeid: 2,
                name: "角",
                rate: 0.1
            }, {
                modeid: 3,
                name: "分",
                rate: 0.01
            }, {
                modeid: 4,
                name: "厘",
                rate: 0.001
            }],
            maxcodecount: 0,
            isrx: 0,
            numcount: 0,
            defaultposition: "00",
            desc: "直选单式"
        }]
    }]
}, {
    isrx: "0",
    isdefault: "0",
    title: "精确前一",
    label: [{
        gtitle: "精确前一",
        label: [{
            selectarea: {
                type: "digital",
                layout: [{
                    title: "冠军",
                    no: "01|02|03|04|05|06|07|08|09|10",
                    place: 0,
                    cols: 1
                }],
                noBigIndex: 5,
                isButton: true
            },
            show_str: "X",
            code_sp: "",
            methodid: 1216,
            name: "直选复式",
            methoddesc: "选择1个号码组成一注。",
            methodhelp: "从第1名到第10名任意位置上，任意选择1个或1个以上号码，进行投注，所选车号的排名和开奖结果一致，即为中奖，反之不中奖。",
            methodexample: "投注方案：01<br>开奖号码：01，即中精准前一。",
            prize: {
                1: "1700.00"
            },
            dyprize: [],
            modes: [{
                modeid: 1,
                name: "元",
                rate: 1
            }, {
                modeid: 2,
                name: "角",
                rate: 0.1
            }, {
                modeid: 3,
                name: "分",
                rate: 0.01
            }, {
                modeid: 4,
                name: "厘",
                rate: 0.001
            }],
            maxcodecount: 0,
            isrx: 0,
            numcount: 0,
            defaultposition: "0",
            desc: "直选复式"
        }]
    }]
}];
