<style type="text/css"> 
*{margin:0;padding:0;font-size:9pt;font-family:arial;}
.ui-slogan{height:25px;}
.ui-slogan img{position:absolute;margin:0 0 0 -20px;}
table.ui-state-hover{border-width:1px;border-style:solid;border-collapse:collapse;}
table.ui-state-hover td{line-height:25px;}
table.ui-state-hover thead td{/*border-bottom-width:1px;border-bottom-style:solid;*/text-align:center}
table.ui-state-hover tbody td{border:1px solid #ccc;text-align:center}
tr.odd{background:#f1f2ff}
form{margin:0;padding:0;display:block}
label{display:inline;font-weight:bold;text-align:right;padding: 0px 5px 3px 2px;}
label[class="error"]{display:inline;font-weight:bold;text-align:right;color: red;}
input{width:;height:;line-height:;}
input[type="checkbox"]{vertical-align:bottom;}
h1{font-size:16pt}
h2{font-size:14pt}
h3{font-size:12pt}
h4{font-size:10pt}
h5{font-size:9pt}

button,a{cursor:pointer;}
button .ui-icon,
a .ui-icon{float:left;}
button .ui-inputHead_t,
a .ui-inputHead_t{
/*float:left;*/
*margin:3px 0 0 3px;clear:right;margin:0px 0 0 3px;}

.x1{width:33%}
.x2{width:66%}
.x3{width:100%}
.x0d5{width:49.9%}
.x5{width:50px}
.x6{width:60px}
.x7{width:70px}
.x8{width:80px}
.x9{width:90px}
.x10{width:100px}
.x11{width:110px}
.x12{width:120px}
.x13{width:130px}
.x14{width:140px}
.x15{width:150px}
.x16{width:160px}
.x18{width:180px}
.x20{width:200px}
.x25{width:250px}
.x30{width:300px}
.l{float:left;}
.ll{float:left; margin:7px 0 0 0;}
.hr{border:0;margin:2px 0;display:block;border-top-width:1px;border-top-style:none;border-top-color:#999;}
.dotted{border-top-style:dotted}
.outset{border-top-style:outset}
.inset{border-top-style:inset}
.solid{border-top-style:solid}
.c{clear:both;font-size:0;line-height:0;height:0;}
.g{color:#999;}

.ui-logic{margin:0;height:100%;/*border:1px solid #333*/}
.ui-space{position:relative;}
.ui-container{margin:0 0 0 0;/*border:1px solid #0c3*/}
.ui-container td .ix{position:relative;top:0;bottom:0;height:auto;}
.ui-container td .x{position:relative;top:3px;line-height:0;}
.ui-column{/*border:1px solid #c30*/}/*column*/
.ui-field{margin:0 0 10px;/*border:1px solid #03c;*/}/*panel*/

/*enable disable panel background*/
.ui-field .ui-widget-overlay{z-index:10;left:auto;top:auto;*margin:0 0 0 -20px;}
.ui-column .ui-field .ui-widget-overlay{*margin:0; z-index:999;}
.fullscreen{overflow:auto}
.ui-inputHead{padding:5px;}
.ui-inputBody{width:100%;*width:;}
.ui-inputHead span{}
.ui-inputHead .ui-icon{position:relative;float:left}
.ui-inputHead .ui-inputHead_t{clear:right}
.ui-inputCon{margin:0 20px;}
.ui-inputBtn{padding:10px 0;}
.ui-inputBtn button{margin:0 5px;height:28px;}
.ui-inputBtn button[disabled]{filter: Alpha(opacity=70);-moz-opacity:.7;opacity:0.7;cursor:default;color:#999;}
.ui-inputBtn button .ui-icon{margin:-1px 0 0;*margin:1px 0 0}
.ui-inputBtn button .ui-inputHead_t{margin:-2px 0 0;*margin:2px 0 0;font-size:10pt}

.ui-inputIn{position:relative;float:left; padding:0 5px 0 0}
.ui-inputIn .l{position:absolute;margin:6px 0 0;*margin:3px 0 0;text-align:100%; width:95px; overflow:hidden; text-overflow:ellipsis ; white-space:nowrap;}
.ui-inputIn .ui-state-error-text{position:absolute;top:2px;right:14px;width:16px;height:16px;overflow:hidden;}
.ui-inputIn .ui-state-error-text .ui-icon-star{margin:-1px 0 0 0;}
.ui-inputIn label.error{position:absolute;color:red;}
.ui-inputInWidth{margin:5px 30px 0 100px;}
.ui-inputInWidth .fake{width:100%;height:100%;overflow:hidden;}
.ui-inputInWidth .fake .ui-state-default{position:absolute;right:30px;margin:-19px 0 0;*margin:-20px 0 0;cursor:pointer;}

.ui-inputInWidth input[type="text"]{display:block;font-size:10pt;width:100%;}
.ui-inputInWidth input[type="password"]{display:block;font-size:10pt;width:100%;}
.ui-inputInWidth input[type="textarea"]{display:block;font-size:10pt;width:100%;}
.ui-inputInWidth textarea{display:block;font-size:10pt;width:100%;text-decoration:none;}
.ui-inputInWidth .fake a{display:block;font-size:10pt;width:100%;text-decoration:none;}
.ui-inputInWidth select{display:block;font-size:10pt;width:100%;text-decoration:none;}
.ui-inputInWidth .fake a{height:18px;*margin:1px 0;_margin:2px 0 1px;overflow:hidden;text-overflow:ellipse;white-space:nowrap;}

.ui-inputInWidth input{height:18px;_height:19px;}
.ui-inputInWidth input[type="file"]{height:19px;*height:19px;}
.ui-inputInWidth select{height:19px;*height:21px;}

.ui-inputInWidth input{border:0;/*background:transparent url(${ctx}/html/common/img/blank.gif);*/border:1px solid #999;border-radius: 3px 3px 3px 3px;padding:1px;}
.ui-inputInWidth .fake a{border:0;/*background:transparent url(${ctx}/html/common/img/blank.gif);*/border:1px solid #999;}
.ui-inputInWidth select{border:0;/*background:transparent url(${ctx}/html/common/img/blank.gif);*/border:1px solid #999;}
.ui-inputInWidth input[readonly]{color:#CCCCCC;background:#F7F7F7;}
.ui-inputInWidth input[disabled]{background:#F7F7F7;}
.ui-inputInWidth input:focus, .ui-inputInWidth textarea:focus {border-color: rgba(82, 168, 236, 0.8);box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1) inset, 0 0 8px rgba(82, 168, 236, 0.6);outline: 0 none;}

.ui-inputInWidth input.error{background:#fee;border-bottom:1px dotted red;}
.ui-inputInWidth .fake a.error{background:#fee;border-bottom:1px dotted red;}
.ui-inputInWidth select.error {background:#fee;border-bottom:1px dotted red;}

.ui-inputInWidth .x{position:absolute;top:9px;right:28px;*width:20px;*height:20px;}
.ui-inputInWidth .x input,
.ui-inputInWidth .x select{border-bottom:0;}
body .ui-logic .ui-inputInWidth .radio{display:inline!important;background:none!important;border:0!important;width:auto;height:auto;}

.pageSlider{font-size:9pt;border:1px dotted #999;padding:0 0 0 10px;border-top:0}
.pageSlider .fc{clear:both;height:0;line-height:0;font-size:0;}
.pageSlider .fl{float:left;}
.pageSlider .g{height:30px;line-height:30px;color:#999;white-space:nowrap;padding:0 10px 0 0;text-align:left}

.pageSlider .bd{height:20px;/*border:1px solid #8ac;border-top:1px solid #accbff;border-left:1px solid #accbff;background:#c3d9ff;*/}
.pageSlider .bd .first{/**background:url(../../images/top/components.gif) -20px -45px;**/}
.pageSlider .bd .prev{/**background:url(../../images/top/components.gif) -40px -45px;**/}
.pageSlider .bd .next{/**background:url(../../images/top/components.gif) -60px -45px;**/}
.pageSlider .bd .last{/**background:url(../../images/top/components.gif) -80px -45px;**/}
.pageSlider .bd button{margin:4px 2px 0;line-height:20px;height:20px;text-align:center;text-decoration:none;/*color:#666;*/}
.pageSlider .bd button span{width:18px;height:18px;}
.pageSlider .bd button:hover{/*background-color:#e0ecff;*/}

.pageSlider .page{width:auto;height:30px;}
.pageSlider .page button{margin:4px 2px 0;padding:0 4px;height:20px;line-height:20px;*line-height:18px;text-align:center;/*text-decoration:none;border:1px solid #8ac;border-top:1px solid #accbff;border-left:1px solid #accbff;background:#c3d9ff;color:#666;*/}
.pageSlider .page button:hover{/*color:#333;background:#e0ecff;*/}
.pageSlider .page b{font-size:10pt;padding:0 4px;color:#000;}

.pageSlider .end{width:140px;white-space:nowrap;}
.pageSlider .end input{display:inline;padding:0 0 0 1px;width:30px;margin-top:4px;}
.pageSlider .end .fl{}
.pageSlider .end .bd button{line-height:20px;}
/*=====================================================*/
/*                  drop down filter list              */
/*=====================================================*/
.dropdwon{background:#faebaf; border:1px solid #c2ad59; z-index:9999;}
.dropdwon p{display:block; margin:1px; text-decoration:none; color:#333; padding:1px; height:15px; text-align:left; pointer:cursor;}
/**********************分割线************************************/
</style>