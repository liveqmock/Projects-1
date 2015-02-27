<%@ tag pageEncoding="UTF-8"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ attribute name="onclick" type="java.lang.String" required="true"%>
<%@ attribute name="formId" type="java.lang.String" required="false"%>
<%@ attribute name="callback" type="java.lang.String" required="false"%>
<%@ attribute name="group" type="java.lang.String" required="false"%>
<script type="text/javascript">
	var ${formId}rule = {};
	$(function(){
		$("#${formId}").find("[name]").each(function(){
			var jn=$(this).attr("name");
			if(!${formId}rule[jn]){
				${formId}rule[jn] = {};
			}
			/*
			* 下面开始配置各个验证属性
			*/
			var p = $(this).parent();
			if(p.attr("class")=="ui-inputInWidth"){
				if(!$(this).attr("illegal"))
					${formId}rule[jn].illegal = true;
				/**
				* required
				*/
				if(p.parent().find(".requiredStar").length>0)
					${formId}rule[jn].required = true;
				/**
				* maxlength
				*/
				if($(this).attr("maxlength") && $(this).attr("maxlength")!="-1")
					${formId}rule[jn].maxlength = Number($(this).attr("maxlength"));
				/**
				* minlength
				*/
				if($(this).attr("minlength"))
					${formId}rule[jn].minlength = Number($(this).attr("minlength"));
				/**
				* rangelength
				*/
				if($(this).attr("rangelength"))
					${formId}rule[jn].rangelength = (eval("["+$(this).attr("rangelength")+"]"));
				/**
				* max
				*/
				if($(this).attr("max"))
					${formId}rule[jn].max = Number($(this).attr("max"));
				/**
				* min
				*/
				if($(this).attr("min"))
					${formId}rule[jn].min = Number($(this).attr("min"));
				/**
				* range
				*/
				if($(this).attr("range"))
					${formId}rule[jn].range = (eval("["+$(this).attr("range")+"]"));
				/**
				* email
				*/
				if($(this).attr("email") == "true")
					${formId}rule[jn].email = true;
				/**
				* number
				*/
				if($(this).attr("datatype") == "number")
					${formId}rule[jn].number = true;
				/**
				* digits
				*/
				if($(this).attr("datatype") == "digits")
					${formId}rule[jn].digits = true;
				/**
				* integer
				*/
				if($(this).attr("datatype") == "integer")
					${formId}rule[jn].integer = true;
					
				/**
				* 数字与字母
				*/
				if($(this).attr("digitLetter") == "true")
					${formId}rule[jn].digitLetter = true;
					
				/**
				* id card
				*/
				if($(this).attr("idcard") == "true")
					${formId}rule[jn].idcard = true;
				/**
				* mobile
				*/
				if($(this).attr("mobile") == "true")
					${formId}rule[jn].mobile = true;
				/**
				* phone
				*/
				if($(this).attr("phone") == "true")
					${formId}rule[jn].phone = true;
        		/**
				* postCode
				*/
				if($(this).attr("isPostCode") == "true")
					${formId}rule[jn].isPostCode = true;					
				/**
				* coordinate calendar
				*/
				if($(this).attr("next") == "true")
					${formId}rule[jn].next = true;
				/**
				* stringMinLength
				*/
				if($(this).attr("stringMinLength") && $(this).attr("stringMinLength")!="-1")
					${formId}rule[jn].stringMinLength = Number($(this).attr("stringMinLength"));
				/**
				* stringMaxLength
				*/
				if($(this).attr("stringMaxLength") && $(this).attr("stringMaxLength")!="-1")
					${formId}rule[jn].stringMaxLength = Number($(this).attr("stringMaxLength"));
				/**
				* string
				*/
				if($(this).attr("string") == "true")
					${formId}rule[jn].string = true;
				/**
				* byteRangeLength
				*/
				if($(this).attr("byteRangeLength"))
					${formId}rule[jn].byteRangeLength = (eval("["+$(this).attr("byteRangeLength")+"]"));
				/**
				* stringCH
				*/
				if($(this).attr("stringCH") == "true")
					${formId}rule[jn].stringCH = true;
				/**
				* stringEN
				*/
				if($(this).attr("stringEN") == "true")
					${formId}rule[jn].stringEN = true;
					
				if($(this).attr("mixPhone") == "true")
					${formId}rule[jn].mixPhone = true;

				if($(this).attr("noFullWidthStr") == "true")
					${formId}rule[jn].noFullWidthStr = true;
				
				/**
				* simplePassword
				*/
				if($(this).attr("simplePassword") == "true")
					${formId}rule[jn].simplePassword = true;
					
				var sametextObj = $(this).attr("sametext");
				if(sametextObj && (sametextObj.length>0)){					                  
                	var attrvs = sametextObj.split(";",2);
                	${formId}rule[jn].sametext = attrvs[0];
       			}
       			
      			if($(this).attr("fixlength"))
					${formId}rule[jn].length = Number($(this).attr("fixlength"));
				
				/**
				 * more比较上id元素要大:param要求是jquery的钩子,如#id或 div #haha,用;分开后面的提示
				 * 用法:
				 *  more="#id_beginno;终止册号小于超始册号"
				 *  当出现本值比$(#id_beginno)小时,会提示"终止册号小于超始册号"
				 * 
				 */
				var moreObj = $(this).attr("more");
				if(moreObj && (moreObj.length>0)){					                  
                	var attrvs = moreObj.split(";",2);
                	${formId}rule[jn].more = attrvs[0];
                	$.validator.messages["more"]=attrvs.length>1?attrvs[1]:"此值要比另一个值大";
				}
				
				/**
				 * samelen  长度要一样
				 */
				var samelenObj = $(this).attr("samelen");
				if(samelenObj && (samelenObj.length>0)){					                  
                	var attrvs = samelenObj.split(";",2);
                	${formId}rule[jn].samelen = attrvs[0];
                	$.validator.messages["samelen"]=attrvs.length>1?attrvs[1]:"两值的长度要一样";
				}
			}
		});
		/**定义验证器**/
		${formId}validator = $("#${formId}").validate({
			debug : false, 
			//autoCreateRanges:true,
			rules : ${formId}rule
		});
		/*
		* overwrite submit
		*/
		$("#${formId}")[0].onsubmit=function(){ 
			if(${formId}validator.form()){
				<c:forEach items="${fn:split(group,',')}" var="d" begin="0">
					<c:if test="${!empty d}">
						if(${d}()==false){
							return false;
						}
					</c:if>
				</c:forEach> 
				<c:if test="${!empty callback}">
						return false;
				</c:if> 
			}else{
				return false;
			} 
		};
		//定义onclick时调用的方法
		${onclick}=function(obj){ 
			if(${formId}validator.form()){
				<c:forEach items="${fn:split(group,',')}" var="d" begin="0">
					<c:if test="${!empty d}">
						if(${d}()==false){
							return false;
						}
					</c:if>
				</c:forEach>
				<c:if test="${!empty callback}">
					${callback}(obj);
				</c:if>
				<c:if test="${empty callback}">
					$("#${formId}").trigger("submit");
					return false;
				</c:if>
			}else{
				return false;
			}
			return true;
		}
	});
</script>