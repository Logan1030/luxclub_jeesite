<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta name="decorator" content="default"/>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link href="${ctxStatic}/flot/examples.css" rel="stylesheet" type="text/css">
	<script language="javascript" type="text/javascript" src="${ctxStatic }/flot/jquery.js"></script>
	<script language="javascript" type="text/javascript" src="${ctxStatic }/flot/jquery.flot.js"></script>
	<script type="text/javascript">

	$(function() {

		var options = {
			lines: {
				show: true
			},
			points: {
				show: true
			},
			xaxis: {
				tickDecimals: 0,
				tickSize: 1
			}
		};

		var data = [];

		$.plot("#placeholder", data, options);

		var alreadyFetched = {};

		$("button.fetchSeries").click(function () {

			var button = $(this);

			var dataurl = button.siblings("a").attr("href");
			$.ajax({
				url: dataurl,
				type: "GET",
				dataType: "json",
				success: function(series){
					if (!alreadyFetched[series.label]) {
						alreadyFetched[series.label] = true;
						data.push(series);
					}
					$.plot("#placeholder", data, options);
				}
			});
		});

		$("button.fetchSeries:first").click();

		$("#footer").prepend("Flot " + $.plot.version + " &ndash; ");
	});

	</script>
</head>
<body>
    <ul class="nav nav-tabs">
		<li ><a href="${ctx}/luxclub/orderstatistics/">统计客户经理业绩</a></li>
		<li><a href="${ctx}/luxclub/orderstatistics/listInfo">统计账务信息</a></li>
		<li class="active"><a href="${ctx}/luxclub/orderstatistics/flot">图表分析图</a></li>
	</ul>
	<div id="header">
		<h2>本月业绩图表</h2>
	</div>

	<div id="content">

		<div class="demo-container">
			<div id="placeholder" class="demo-placeholder"></div>
		</div>
		<p>
			<button class="fetchSeries">本月数据显示</button>
			<a href="${ctx}/luxclub/orderstatistics/getFlotData"></a> 
			<span></span>
		</p>

	</div>

	 
</body>
</html>
