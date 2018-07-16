(function($){
$(document).ready(function(){
	$.playInit({ 
		data_label: [
										{title:'后三直选',label:[{methoddesc:'从百位、十位、个位中至少各选1个号码。',
methodhelp:'从百位、十位、个位中选择一个3位数号码组成一注，所选号码与开奖号码后3位相同，且顺序一致，即为中奖。',
                                    selectarea:{
                                               type   : 'digital',
                                               layout : [
                                                           {title:'百位', no:'0|1|2|3|4|5|6|7|8|9', place:0, cols:1},
                                                           {title:'十位', no:'0|1|2|3|4|5|6|7|8|9', place:1, cols:1},
                                                           {title:'个位', no:'0|1|2|3|4|5|6|7|8|9', place:2, cols:1}
                                                          ],
                                               noBigIndex : 5,
                                               isButton   : true
                                              },
                                    show_str : '-,-,X,X,X',
                                    code_sp : '',
                                                  methodid : 16,
                                                  name:'复式',
                                                  prize:{1:'1800.00'},
                                                  dyprize:[{"level":1,"prize":[{"point":"0.025","prize":1800},{"point":0,"prize":1850}]}],
                                                  modes:[{modeid:1,name:'元',rate:1},{modeid:2,name:'角',rate:0.1}],
                                                  desc:'直选复式'
                                                },{methoddesc:'手动输入号码，至少输入1个三位数号码组成一注。',
methodhelp:'手动输入一个3位数号码组成一注，所选号码与开奖号码的百位、十位、个位相同，且顺序一致，即为中奖。',
                                    selectarea:{type:'input'},
                                    show_str : 'X',
                                    code_sp : ' ',
                                                  methodid : 16,
                                                  name:'单式',
                                                  prize:{1:'1800.00'},
                                                  dyprize:[{"level":1,"prize":[{"point":"0.025","prize":1800},{"point":0,"prize":1850}]}],
                                                  modes:[{modeid:1,name:'元',rate:1},{modeid:2,name:'角',rate:0.1}],
                                                  desc:'直选单式'
                                                },{methoddesc:'从0-27中任意选择1个或1个以上号码',
methodhelp:'所选数值等于开奖号码的百位、十位、个位三个数字相加之和，即为中奖。',
                                    selectarea:{
                                               type   : 'digital',
                                               layout : [
                                                           {title:'和值', no:'0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|25|26|27', place:0, cols:1}
                                                         ],
                                               isButton   : false
                                              },
                                    show_str : 'X',
                                    code_sp : ',',
                                                  methodid : 17,
                                                  name:'和值',
                                                  prize:{1:'1800.00'},
                                                  dyprize:[{"level":1,"prize":[{"point":"0.025","prize":1800},{"point":0,"prize":1850}]}],
                                                  modes:[{modeid:1,name:'元',rate:1},{modeid:2,name:'角',rate:0.1}],
                                                  desc:'直选和值'
                                                }]},										
                                         {title:'后三组选',label:[{methoddesc:'从0-9中任意选择2个或2个以上号码。',
methodhelp:'从0-9中选择2个数字组成两注，所选号码与开奖号码的百位、十位、个位相同，且顺序不限，即为中奖。',
                                    selectarea:{
                                               type   : 'digital',
                                               layout : [
                                                           {title:'组三', no:'0|1|2|3|4|5|6|7|8|9', place:0, cols:1}
                                                          ],
                                               noBigIndex : 5,
                                               isButton   : true
                                              },
                                    show_str : 'X',
                                    code_sp : ',',
                                                  methodid : 22,
                                                  name:'组三',
                                                  prize:{1:'600.00'},
                                                  dyprize:[{"level":1,"prize":[{"point":"0.025","prize":600},{"point":0,"prize":616}]}],
                                                  modes:[{modeid:1,name:'元',rate:1},{modeid:2,name:'角',rate:0.1}],
                                                  desc:'组三'
                                                },{methoddesc:'从0-9中任意选择3个或3个以上号码。',
methodhelp:'从0-9中任意选择3个号码组成一注，所选号码与开奖号码的百位、十位、个位相同，顺序不限，即为中奖。',
                                    selectarea:{
                                               type   : 'digital',
                                               layout : [
                                                           {title:'组六', no:'0|1|2|3|4|5|6|7|8|9', place:0, cols:1}
                                                          ],
                                               noBigIndex : 5,
                                               isButton   : true
                                              },
                                    show_str : 'X',
                                    code_sp : ',',
                                                  methodid : 23,
                                                  name:'组六',
                                                  prize:{2:'300.00'},
                                                  dyprize:[{"level":2,"prize":[{"point":"0.025","prize":300},{"point":0,"prize":308}]}],
                                                  modes:[{modeid:1,name:'元',rate:1},{modeid:2,name:'角',rate:0.1}],
                                                  desc:'组六'
                                                },{methoddesc:'手动输入号码，至少输入1个三位数号码。',
methodhelp:'键盘手动输入购买号码，3个数字为一注，开奖号码的百位、十位、个位符合后三组三或组六均为中奖。',
                                    selectarea:{type:'input'},
                                    show_str : 'X',
                                    code_sp : ' ',
                                                  methodid : 24,
                                                  name:'混合',
                                                  prize:{1:'600.00',2:'300.00'},
                                                  dyprize:[{"level":1,"prize":[{"point":"0.025","prize":600},{"point":0,"prize":616}]},{"level":2,"prize":[{"point":"0.025","prize":600},{"point":0,"prize":616},{"point":"0.025","prize":300},{"point":0,"prize":308}]}],
                                                  modes:[{modeid:1,name:'元',rate:1},{modeid:2,name:'角',rate:0.1}],
                                                  desc:'混合组选'
                                                },{methoddesc:'从1-26中任意选择1个或1个以上号码。',
methodhelp:'所选数值等于开奖号码百位、十位、个位三个数字相加之和，即为中奖。',
                                    selectarea:{
                                               type   : 'digital',
                                               layout : [
                                                           {title:'和值', no:'1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|25|26', place:0, cols:1}
                                                         ],
                                               isButton   : false
                                              },
                                    show_str : 'X',
                                    code_sp : ',',
                                                  methodid : 25,
                                                  name:'和值',
                                                  prize:{1:'600.00',2:'300.00'},
                                                  dyprize:[{"level":1,"prize":[{"point":"0.025","prize":600},{"point":0,"prize":616}]},{"level":2,"prize":[{"point":"0.025","prize":600},{"point":0,"prize":616},{"point":"0.025","prize":300},{"point":0,"prize":308}]}],
                                                  modes:[{modeid:1,name:'元',rate:1},{modeid:2,name:'角',rate:0.1}],
                                                  desc:'组选和值'
                                                }]},										{title:'后三不定位',label:[{methoddesc:'从0-9中任意选择1个或1个以上号码。',
methodhelp:'从0-9中选择1个号码，每注由1个号码组成，只要开奖号码的百位、十位、个位中包含所选号码，即为中奖。',
                                    selectarea:{
                                               type   : 'digital',
                                               layout : [
                                                           {title:'一码', no:'0|1|2|3|4|5|6|7|8|9', place:0, cols:1}
                                                          ],
                                               noBigIndex : 5,
                                               isButton   : true
                                              },
                                    show_str : 'X',
                                    code_sp : ',',
                                                  methodid : 26,
                                                  name:'一码',
                                                  prize:{1:'6.60'},
                                                  dyprize:[{"level":1,"prize":[{"point":"0.025","prize":6.6},{"point":0,"prize":6.7}]}],
                                                  modes:[{modeid:1,name:'元',rate:1},{modeid:2,name:'角',rate:0.1}],
                                                  desc:'一码不定位'
                                                },{methoddesc:'从0-9中任意选择2个或2个以上号码。',
methodhelp:'从0-9中选择2个号码，每注由2个不同的号码组成，开奖号码的百位、十位、个位中同时包含所选的2个号码，即为中奖。',
                                    selectarea:{
                                               type   : 'digital',
                                               layout : [
                                                           {title:'二码', no:'0|1|2|3|4|5|6|7|8|9', place:0, cols:1}
                                                          ],
                                               noBigIndex : 5,
                                               isButton   : true
                                              },
                                    show_str : 'X',
                                    code_sp : ',',
                                                  methodid : 27,
                                                  name:'二码',
                                                  prize:{1:'33.00'},
                                                  dyprize:[{"level":1,"prize":[{"point":"0.025","prize":33},{"point":0,"prize":33.9}]}],
                                                  modes:[{modeid:1,name:'元',rate:1},{modeid:2,name:'角',rate:0.1}],
                                                  desc:'二码不定位'
                                                }]},										{title:'前三直选',label:[{methoddesc:'从万位、千位、百位中至少各选1个号码。',
methodhelp:'从万位、千位、百位中选择一个3位数号码组成一注，所选号码与开奖号码前3位相同，且顺序一致，即为中奖。',
selectarea:{type:'digital',layout:[{title:'万位', no:'0|1|2|3|4|5|6|7|8|9', place:0, cols:1},{title:'千位', no:'0|1|2|3|4|5|6|7|8|9', place:1, cols:1},{title:'百位', no:'0|1|2|3|4|5|6|7|8|9', place:2, cols:1}],noBigIndex:5,isButton:true},
show_str : 'X,X,X,-,-',
code_sp  : '',
                                                  methodid : 14,
                                                  name:'复式',
                                                  prize:{1:'1800.00'},
                                                  dyprize:[{"level":1,"prize":[{"point":"0.025","prize":1800},{"point":0,"prize":1850}]}],
                                                  modes:[{modeid:1,name:'元',rate:1},{modeid:2,name:'角',rate:0.1}],
                                                  desc:'直选复式'
                                                },{methoddesc:'手动输入号码，至少输入1个三位数号码组成一注。',
methodhelp:'手动输入一个3位数号码组成一注，所选号码与开奖号码的万位、千位、百位相同，且顺序一致，即为中奖。',
selectarea:{type:'input'},
show_str : 'X',
code_sp : ' ',
                                                  methodid : 14,
                                                  name:'单式',
                                                  prize:{1:'1800.00'},
                                                  dyprize:[{"level":1,"prize":[{"point":"0.025","prize":1800},{"point":0,"prize":1850}]}],
                                                  modes:[{modeid:1,name:'元',rate:1},{modeid:2,name:'角',rate:0.1}],
                                                  desc:'直选单式'
                                                },{methoddesc:'从0-27中任意选择1个或1个以上号码。',
methodhelp:'所选数值等于开奖号码万位、千位、百位三个数字相加之和，即为中奖。',
                                    selectarea:{
                                               type   : 'digital',
                                               layout : [
                                                           {title:'和值', no:'0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|25|26|27', place:0, cols:1}
                                                         ],
                                               isButton   : false
                                              },
                                    show_str : 'X',
                                    code_sp : ',',
                                                  methodid : 15,
                                                  name:'和值',
                                                  prize:{1:'1800.00'},
                                                  dyprize:[{"level":1,"prize":[{"point":"0.025","prize":1800},{"point":0,"prize":1850}]}],
                                                  modes:[{modeid:1,name:'元',rate:1},{modeid:2,name:'角',rate:0.1}],
                                                  desc:'直选和值'
                                                }]},										{title:'前三组选',label:[{methoddesc:'从0-9中任意任选2个或2个以上号码。',
methodhelp:'从0-9中选择2个数字组成两注，所选号码与开奖号码的万位、千位、百位相同，且顺序不限，即为中奖。',
                                    selectarea:{
                                               type   : 'digital',
                                               layout : [
                                                           {title:'组三', no:'0|1|2|3|4|5|6|7|8|9', place:0, cols:1}
                                                          ],
                                               noBigIndex : 5,
                                               isButton   : true
                                              },
                                    show_str : 'X',
                                    code_sp : ',',
                                                  methodid : 18,
                                                  name:'组三',
                                                  prize:{1:'600.00'},
                                                  dyprize:[{"level":1,"prize":[{"point":"0.025","prize":600},{"point":0,"prize":616}]}],
                                                  modes:[{modeid:1,name:'元',rate:1},{modeid:2,name:'角',rate:0.1}],
                                                  desc:'组三'
                                                },{methoddesc:'从0-9中任意任选3个或3个以上号码。',
methodhelp:'从0-9中任意选择3个号码组成一注，所选号码与开奖号码的万位、千位、百位相同，顺序不限，即为中奖。',
                                    selectarea:{
                                               type   : 'digital',
                                               layout : [
                                                           {title:'组六', no:'0|1|2|3|4|5|6|7|8|9', place:0, cols:1}
                                                          ],
                                               noBigIndex : 5,
                                               isButton   : true
                                              },
                                    show_str : 'X',
                                    code_sp : ',',
                                                  methodid : 19,
                                                  name:'组六',
                                                  prize:{2:'300.00'},
                                                  dyprize:[{"level":2,"prize":[{"point":"0.025","prize":300},{"point":0,"prize":308}]}],
                                                  modes:[{modeid:1,name:'元',rate:1},{modeid:2,name:'角',rate:0.1}],
                                                  desc:'组六'
                                                },{methoddesc:'手动输入号码，至少输入1个三位数号码组成一注。',
methodhelp:'键盘手动输入购买号码，3个数字为一注，开奖号码的万位、千位、百位符合前三的组三或组六均为中奖。',
                                    selectarea:{type:'input'},
                                    show_str : 'X',
                                    code_sp : ' ',
                                                  methodid : 20,
                                                  name:'混合',
                                                  prize:{1:'600.00',2:'300.00'},
                                                  dyprize:[{"level":1,"prize":[{"point":"0.025","prize":600},{"point":0,"prize":616}]},{"level":2,"prize":[{"point":"0.025","prize":600},{"point":0,"prize":616},{"point":"0.025","prize":300},{"point":0,"prize":308}]}],
                                                  modes:[{modeid:1,name:'元',rate:1},{modeid:2,name:'角',rate:0.1}],
                                                  desc:'混合组选'
                                                },{methoddesc:'从1-26中任意选择1个或1个以上号码。',
methodhelp:'所选数值等于开奖号码万位、千位、百位三个数字相加之和，即为中奖。',
                                    selectarea:{
                                               type   : 'digital',
                                               layout : [
                                                           {title:'和值', no:'1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|25|26', place:0, cols:1}
                                                         ],
                                               isButton   : false
                                              },
                                    show_str : 'X',
                                    code_sp : ',',
                                                  methodid : 21,
                                                  name:'和值',
                                                  prize:{1:'600.00',2:'300.00'},
                                                  dyprize:[{"level":1,"prize":[{"point":"0.025","prize":600},{"point":0,"prize":616}]},{"level":2,"prize":[{"point":"0.025","prize":600},{"point":0,"prize":616},{"point":"0.025","prize":300},{"point":0,"prize":308}]}],
                                                  modes:[{modeid:1,name:'元',rate:1},{modeid:2,name:'角',rate:0.1}],
                                                  desc:'组选和值'
                                                }]},										{title:'二星',label:[{methoddesc:'从十位和个位上至少各选1个号码。',
methodhelp:'从十位和个位上至少各选1个号码，所选号码与开奖号码的十位、个位相同，且顺序一致，即为中奖。',
                                    selectarea:{
                                               type   : 'digital',
                                               layout : [
                                                           {title:'十位', no:'0|1|2|3|4|5|6|7|8|9', place:0, cols:1},
                                                           {title:'个位', no:'0|1|2|3|4|5|6|7|8|9', place:1, cols:1}
                                                          ],
                                               noBigIndex : 5,
                                               isButton   : true
                                              },
                                    show_str : '-,-,-,X,X',
                                    code_sp : '',
                                                  methodid : 29,
                                                  name:'后二直选复式',
                                                  prize:{1:'180.00'},
                                                  dyprize:[{"level":1,"prize":[{"point":"0.025","prize":180},{"point":0,"prize":185}]}],
                                                  modes:[{modeid:1,name:'元',rate:1},{modeid:2,name:'角',rate:0.1}],
                                                  desc:'后二直选复式'
                                                },{methoddesc:'手动输入号码，至少输入1个两位数号码。',
methodhelp:'手动输入一个2位数号码组成一注，所选号码与开奖号码的十位、个位相同，且顺序一致，即为中奖。',
selectarea:{type:'input'},
show_str : 'X',
code_sp : ' ',
                                                  methodid : 29,
                                                  name:'后二直选单式',
                                                  prize:{1:'180.00'},
                                                  dyprize:[{"level":1,"prize":[{"point":"0.025","prize":180},{"point":0,"prize":185}]}],
                                                  modes:[{modeid:1,name:'元',rate:1},{modeid:2,name:'角',rate:0.1}],
                                                  desc:'后二直选单式'
                                                },{methoddesc:'从0-9中任意选择2个或2个以上号码。',
methodhelp:'从0-9中选2个号码组成一注，所选号码与开奖号码的十位、个位相同，顺序不限，即中奖。',
                                    selectarea:{
                                               type   : 'digital',
                                               layout : [
                                                           {title:'组选', no:'0|1|2|3|4|5|6|7|8|9', place:0, cols:1}
                                                          ],
                                               noBigIndex : 5,
                                               isButton   : true
                                              },
                                    show_str : 'X',
                                    code_sp : ',',
                                                  methodid : 31,
                                                  name:'后二组选复式',
                                                  prize:{1:'90.00'},
                                                  dyprize:[{"level":1,"prize":[{"point":"0.025","prize":90},{"point":0,"prize":92}]}],
                                                  modes:[{modeid:1,name:'元',rate:1},{modeid:2,name:'角',rate:0.1}],
                                                  desc:'后二组选复式'
                                                },{methoddesc:'手动输入号码，至少输入1个两位数号码。',
methodhelp:'手动输入购买号码，2个数字为一注，所选号码与开奖号码的十位、个位相同，顺序不限，即为中奖。',
selectarea:{type:'input'},
show_str : 'X',
code_sp : ' ',
                                                  methodid : 31,
                                                  name:'后二组选单式',
                                                  prize:{1:'90.00'},
                                                  dyprize:[{"level":1,"prize":[{"point":"0.025","prize":90},{"point":0,"prize":92}]}],
                                                  modes:[{modeid:1,name:'元',rate:1},{modeid:2,name:'角',rate:0.1}],
                                                  desc:'后二组选单式'
                                                },{methoddesc:'从万位和千位上至少各选1个号码。',
methodhelp:'从万位和千位上至少各选1个号码，所选号码与开奖号码的万位、千位相同，且顺序一致，即为中奖。',
                                    selectarea:{
                                               type   : 'digital',
                                               layout : [
                                                           {title:'万位', no:'0|1|2|3|4|5|6|7|8|9', place:0, cols:1},
                                                           {title:'千位', no:'0|1|2|3|4|5|6|7|8|9', place:1, cols:1}
                                                          ],
                                               noBigIndex : 5,
                                               isButton   : true
                                              },
                                    show_str : 'X,X,-,-,-',
                                    code_sp : '',
                                                  methodid : 28,
                                                  name:'前二直选复式',
                                                  prize:{1:'180.00'},
                                                  dyprize:[{"level":1,"prize":[{"point":"0.025","prize":180},{"point":0,"prize":185}]}],
                                                  modes:[{modeid:1,name:'元',rate:1},{modeid:2,name:'角',rate:0.1}],
                                                  desc:'前二直选复式'
                                                },{methoddesc:'手动输入号码，至少输入1个两位数号码。',
methodhelp:'手动输入一个2位数号码组成一注，所选号码与开奖号码的万位、千位相同，且顺序一致，即为中奖。',
selectarea:{type:'input'},
show_str : 'X',
code_sp : ' ',
                                                  methodid : 28,
                                                  name:'前二直选单式',
                                                  prize:{1:'180.00'},
                                                  dyprize:[{"level":1,"prize":[{"point":"0.025","prize":180},{"point":0,"prize":185}]}],
                                                  modes:[{modeid:1,name:'元',rate:1},{modeid:2,name:'角',rate:0.1}],
                                                  desc:'前二直选单式'
                                                },{methoddesc:'从0-9中任意选择2个或2个以上号码。',
methodhelp:'从0-9中选2个号码组成一注，所选号码与开奖号码的万位、千位相同，顺序不限，即为中奖。',
                                    selectarea:{
                                               type   : 'digital',
                                               layout : [
                                                           {title:'组选', no:'0|1|2|3|4|5|6|7|8|9', place:0, cols:1}
                                                          ],
                                               noBigIndex : 5,
                                               isButton   : true
                                              },
                                    show_str : 'X',
                                    code_sp : ',',
                                                  methodid : 30,
                                                  name:'前二组选复式',
                                                  prize:{1:'90.00'},
                                                  dyprize:[{"level":1,"prize":[{"point":"0.025","prize":90},{"point":0,"prize":92}]}],
                                                  modes:[{modeid:1,name:'元',rate:1},{modeid:2,name:'角',rate:0.1}],
                                                  desc:'前二组选复式'
                                                },{methoddesc:'手动输入号码，至少输入1个两位数号码。',
methodhelp:'手动输入购买号码，2个数字为一注，所选号码与开奖号码的万位、千位相同，顺序不限，即为中奖。',
selectarea:{type:'input'},
show_str : 'X',
code_sp : ' ',
                                                  methodid : 30,
                                                  name:'前二组选单式',
                                                  prize:{1:'90.00'},
                                                  dyprize:[{"level":1,"prize":[{"point":"0.025","prize":90},{"point":0,"prize":92}]}],
                                                  modes:[{modeid:1,name:'元',rate:1},{modeid:2,name:'角',rate:0.1}],
                                                  desc:'前二组选单式'
                                                }]},										{title:'定位胆',label:[{methoddesc:'在万位，千位，百位，十位，个位任意位置上任意选择1个或1个以上号码。',
methodhelp:'从万位、千位、百位、十位、个位任意1个位置或多个位置上选择1个号码，所选号码与相同位置上的开奖号码一致，即为中奖。',
                                    selectarea:{
                                               type   : 'digital',
                                               layout : [
                                                           {title:'万位', no:'0|1|2|3|4|5|6|7|8|9', place:0, cols:1},
                                                           {title:'千位', no:'0|1|2|3|4|5|6|7|8|9', place:1, cols:1},
                                                           {title:'百位', no:'0|1|2|3|4|5|6|7|8|9', place:2, cols:1},
                                                           {title:'十位', no:'0|1|2|3|4|5|6|7|8|9', place:3, cols:1},
                                                           {title:'个位', no:'0|1|2|3|4|5|6|7|8|9', place:4, cols:1}
                                                          ],
                                               noBigIndex : 5,
                                               isButton   : true
                                              },
                                    show_str : 'X,X,X,X,X',
                                    code_sp : '',
                                                  methodid : 32,
                                                  name:'定位胆',
                                                  prize:{1:'18.00'},
                                                  dyprize:[{"level":1,"prize":[{"point":"0.025","prize":18},{"point":0,"prize":18.5}]}],
                                                  modes:[{modeid:1,name:'元',rate:1},{modeid:2,name:'角',rate:0.1}],
                                                  desc:'定位胆'
                                                }]},										{title:'大小单双',label:[{methoddesc:'从万位、千位中的“大、小、单、双”中至少各选一个组成一注。',
methodhelp:'对万位和千位的“大（56789）小（01234）、单（13579）双（02468）”形态进行购买，所选号码的位置、形态与开奖号码的位置、形态相同，即为中奖。',
                                    selectarea:{
                                                type:'dxds',
                                                layout: [{title:'万位', no:'大|小|单|双', place:0, cols:1},
                                                         {title:'千位', no:'大|小|单|双', place:1, cols:1}]
                                              },
                                    show_str : 'X,X',
                                    code_sp : '',
                                                  methodid : 37,
                                                  name:'前二',
                                                  prize:{1:'7.20'},
                                                  dyprize:[{"level":1,"prize":[{"point":"0.025","prize":7.2},{"point":0,"prize":7.4}]}],
                                                  modes:[{modeid:1,name:'元',rate:1},{modeid:2,name:'角',rate:0.1}],
                                                  desc:'前二大小单双'
                                                },{methoddesc:'从十位、个位中的“大、小、单、双”中至少各选一个组成一注。',
methodhelp:'对十位和个位的“大（56789）小（01234）、单（13579）双（02468）”形态进行购买，所选号码的位置、形态与开奖号码的位置、形态相同，即为中奖。',
                                    selectarea:{
                                                type:'dxds',
                                                layout: [{title:'十位', no:'大|小|单|双', place:0, cols:1},
                                                         {title:'个位', no:'大|小|单|双', place:1, cols:1}]
                                              },
                                    show_str : 'X,X',
                                    code_sp : '',
                                                  methodid : 38,
                                                  name:'后二',
                                                  prize:{1:'7.20'},
                                                  dyprize:[{"level":1,"prize":[{"point":"0.025","prize":7.2},{"point":0,"prize":7.4}]}],
                                                  modes:[{modeid:1,name:'元',rate:1},{modeid:2,name:'角',rate:0.1}],
                                                  desc:'后二大小单双'
                                                }]}										],
		cur_issue : {issue:'131210073',endtime:'2013-12-10 18:08:30',opentime:'2013-12-10 18:10:30'},
		issues    : {//所有的可追号期数集合
                         today:[
                                {issue:'131210073',endtime:'2013-12-10 18:08:30'},								{issue:'131210074',endtime:'2013-12-10 18:18:30'},								{issue:'131210075',endtime:'2013-12-10 18:28:30'},								{issue:'131210076',endtime:'2013-12-10 18:38:30'},								{issue:'131210077',endtime:'2013-12-10 18:48:30'},								{issue:'131210078',endtime:'2013-12-10 18:58:30'},								{issue:'131210079',endtime:'2013-12-10 19:08:30'},								{issue:'131210080',endtime:'2013-12-10 19:18:30'},								{issue:'131210081',endtime:'2013-12-10 19:28:30'},								{issue:'131210082',endtime:'2013-12-10 19:38:30'},								{issue:'131210083',endtime:'2013-12-10 19:48:30'},								{issue:'131210084',endtime:'2013-12-10 19:58:30'},								{issue:'131210085',endtime:'2013-12-10 20:08:30'},								{issue:'131210086',endtime:'2013-12-10 20:18:30'},								{issue:'131210087',endtime:'2013-12-10 20:28:30'},								{issue:'131210088',endtime:'2013-12-10 20:38:30'},								{issue:'131210089',endtime:'2013-12-10 20:48:30'},								{issue:'131210090',endtime:'2013-12-10 20:58:30'},								{issue:'131210091',endtime:'2013-12-10 21:08:30'},								{issue:'131210092',endtime:'2013-12-10 21:18:30'},								{issue:'131210093',endtime:'2013-12-10 21:28:30'},								{issue:'131210094',endtime:'2013-12-10 21:38:30'},								{issue:'131210095',endtime:'2013-12-10 21:48:30'},								{issue:'131210096',endtime:'2013-12-10 21:58:30'},								{issue:'131210097',endtime:'2013-12-10 22:04:10'},								{issue:'131210098',endtime:'2013-12-10 22:09:10'},								{issue:'131210099',endtime:'2013-12-10 22:14:10'},								{issue:'131210100',endtime:'2013-12-10 22:19:10'},								{issue:'131210101',endtime:'2013-12-10 22:24:10'},								{issue:'131210102',endtime:'2013-12-10 22:29:10'},								{issue:'131210103',endtime:'2013-12-10 22:34:10'},								{issue:'131210104',endtime:'2013-12-10 22:39:10'},								{issue:'131210105',endtime:'2013-12-10 22:44:10'},								{issue:'131210106',endtime:'2013-12-10 22:49:10'},								{issue:'131210107',endtime:'2013-12-10 22:54:10'},								{issue:'131210108',endtime:'2013-12-10 22:59:10'},								{issue:'131210109',endtime:'2013-12-10 23:04:10'},								{issue:'131210110',endtime:'2013-12-10 23:09:10'},								{issue:'131210111',endtime:'2013-12-10 23:14:10'},								{issue:'131210112',endtime:'2013-12-10 23:19:10'},								{issue:'131210113',endtime:'2013-12-10 23:24:10'},								{issue:'131210114',endtime:'2013-12-10 23:29:10'},								{issue:'131210115',endtime:'2013-12-10 23:34:10'},								{issue:'131210116',endtime:'2013-12-10 23:39:10'},								{issue:'131210117',endtime:'2013-12-10 23:44:10'},								{issue:'131210118',endtime:'2013-12-10 23:49:10'},								{issue:'131210119',endtime:'2013-12-10 23:54:10'},								{issue:'131210120',endtime:'2013-12-10 23:59:10'}								                               ],
                         tomorrow: [
                                                               ]
                     },
		servertime: '2013-12-10 18:02:58',
		lotteryid : parseInt(1,10),
		isdynamic : 1,
		//onfinishbuy: function(){window.parent.abcd();},
		ajaxurl   : './play_cqssc.shtml'
	});
});
})(jQuery);