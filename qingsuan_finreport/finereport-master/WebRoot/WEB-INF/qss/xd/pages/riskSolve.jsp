<%--
  Created by IntelliJ IDEA.
  User: wangfeng
  Date: 2018/4/2
  Time: 14:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="zh-CN">
<head>
    <meta charset="UTF-8" name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link rel="shortcut icon" href="${contextPath}/static/img/com/logo.png">
    <link rel="stylesheet" href="${contextPath}/static/asserts/css/normalize.css">
    <link rel="stylesheet" href="${contextPath}/static/css/style.css">
    <link rel="stylesheet" href="${contextPath}/static/css/pageCom.css">
    <style>
        .model{

            background: url("${contextPath}/static/img/com/bg_main.jpg") no-repeat;
            position: absolute;
            left: 50%;
            top: 50%;
            /*width: 600px;*/
            /*height: 400px;*/
            /*margin-left: -300px;*/
            /*margin-top: -200px;*/
            border-radius: .5rem .5rem 0 0;
            width: 50%;
            height: 60%;
            margin-left: -25%;
            margin-top: -15%;

        }
        .header{
            position: relative;
            height: 2rem;
            height: 8%;
            border-radius: .5rem .5rem 0 0;
            background-color: #0000ff;
        }
        .header span{
            position: absolute;
            right: .8rem;
            top: 0.3rem;
            width: 1rem;
            height: 1rem;
            text-align: center;
            background-color: #fff;
            border-radius: 50%;
            line-height: 1rem;
        }
        .model_content{
            height: 94%;
            padding: 0rem 1rem;

        }
        .mySelect,label{
            font-size: 0.7rem;
        }
        .model_content_information1,.model_content_information2{
            height: 30%;
            border-bottom: 2px solid #00abde;
        }
        .model_content_informationleft, .model_content_informationmiddle{
            width: 30%;
        }
        .tableBox tbody tr td:nth-child(1) a{
            color: red;
        }
    </style>
</head>
<body class="whitePage" id="05_01fengxianshijianchuli" data-title="风险事件处理">
<div class="container">
    <nav class="pagination">
        <ul>
            <li class="home">首页</li>
            <li class="split">|</li>
            <li class="prev">上一页</li>
            <li class="split">|</li>
            <li class="pageNumber">
                <label for="pageCurrent" hidden></label>
                <input id="pageCurrent" type="number" value="1">&nbsp;/
                <span class="pageTotal">30</span>
            </li>
            <li class="split">|</li>
            <li class="next">下一页</li>
            <li class="split">|</li>
            <li class="end">末页</li>
            <li class="output">
                <a class="dropDown">输出</a>
                <ul>
                    <li class="pdf"><span class="icon"></span> PDF</li>
                    <li class="excel"><span class="icon"></span>
                        <a class="dropDown">EXCEL</a>
                        <ul>
                            <li>分页导出</li>
                            <li>原样导出</li>
                            <li>分页分sheet导出</li>
                        </ul>
                    </li>
                    <li class="word"><span class="icon"></span> WORD</li>
                    <li class="picture"><span class="icon"></span> 图片</li>
                </ul>
            </li>
        </ul>
    </nav>
    <div class="formBox open">
        <form action="">
            <div class="inputGroup">
                <label for="eventId">事件ID：</label>
                <input id="eventId">
            </div>
            <div class="inputGroup">
                <label for="customerName">客户名称：</label>
                <input id="customerName">
            </div>
            <div class="inputGroup">
                <label for="marketName">市场名称：</label>
                <select id="marketName">
                    <option value=""></option>
                    <option value="">青岛国际版权</option>
                    <option value="">上海石油化工</option>
                    <option value="">中韩文化产权</option>
                    <option value="">山东天地红</option>
                    <option value="">青岛国新大宗</option>
                    <option value="">联合信用资产</option>
                    <option value="">吉林文化产权</option>
                    <option value="">青岛华银商品</option>
                    <option value="">青岛华益金安</option>
                    <option value="">中国国金(青岛)</option>

                </select>
            </div>
            <br>
            <div class="inputGroup">
                <label for="processingState">处理状态：</label>
                <select id="processingState">
                    <option value=""></option>
                    <option value="">未处理</option>
                    <option value="">驳回</option>
                    <option value="">不选</option>
                </select>
            </div>
            <div class="inputGroup">
                <label for="riskCategories">风险类别：</label>
                <select id="riskCategories">
                    <option value=""></option>
                    <option value="">舆情风险</option>
                    <option value="">资金风险</option>
                    <option value="">客户风险</option>
                    <option value="">工商风险</option>
                    <option value="">交易风险</option>
                    <option value="">商品风险</option>
                    <option value="">不选</option>
                </select>
            </div>
            <div class="inputGroup">
                <label for="riskIndicators">风险指标：</label>
                <select id="riskIndicators">
                    <option value=""></option>

                </select>
            </div>
            <br>
            <div class="inputGroup">
                <label for="alarmTimeStart">报警时间起：</label>
                <input id="alarmTimeStart" type="date">
            </div>
            <div class="inputGroup">
                <label for="alarmTimeEnd">报警时间起：</label>
                <input id="alarmTimeEnd" type="date">
            </div>
            <div class="inputGroup">
                <label for="encode">业务编码：</label>
                <input id="encode">
            </div>
            <div class="btnGroup search">
                <button class="active">查询</button>
            </div>
        </form>
    </div>
    <div class="formFoldHandle"></div>
    <p class="queryResult">此次为您查询数据<span class="number">296</span>条</p>
    <div class="tableBox">
        <table>
            <thead>
            <tr>
                <th>风险事件ID</th>
                <th>报警时间</th>
                <th>风险类别</th>
                <th>风险指标</th>
                <th>风险指标值</th>
                <th>阙值</th>
                <th>超出额</th>
                <th>市场代码</th>
                <th>市场名称</th>
                <th>客户号</th>
                <th>客户名称</th>
                <th>业务菜单编码</th>
                <th>业务菜单名称</th>
                <th>业务类型</th>
                <th>业务编码</th>
                <th>处理人</th>
                <th>处理状态</th>
                <th>处理时间</th>
                <th>风险说明</th>

            </tr>
            </thead>
            <tbody>
            <tr>
                <td><a href="" class="fengxian001">449684</a></td>
                <td>20180124 06:01:43</td>
                <td>资金风险</td>
                <td>出入金异常</td>
                <td>12</td>
                <td>10</td>
                <td>20%</td>
                <td>0011</td>
                <td>山东天地红</td>
                <td>001100001130</td>
                <td>黄啸</td>
                <td>ZJXX</td>
                <td>资金信息</td>
                <td>市场编码</td>
                <td>资金信息</td>
                <td>man</td>
                <td>已提交</td>
                <td>20180124 16:07:39</td>
                <td class="more"><a href="">查看</a></td>

            </tr>
            <tr>
                <td><a href="">449684</a></td>
                <td>20180124 06:01:43</td>
                <td>资金风险</td>
                <td>出入金异常</td>
                <td>12</td>
                <td>10</td>
                <td>20%</td>
                <td>0011</td>
                <td>山东天地红</td>
                <td>001100001130</td>
                <td>黄啸</td>
                <td>ZJXX</td>
                <td>资金信息</td>
                <td>市场编码</td>
                <td>资金信息</td>
                <td>man</td>
                <td>已提交</td>
                <td>20180124 16:07:39</td>
                <td class="more"><a href="">查看</a></td>
            </tr>
            <tr>
                <td><a href="">449684</a></td>
                <td>20180124 06:01:43</td>
                <td>资金风险</td>
                <td>出入金异常</td>
                <td>12</td>
                <td>10</td>
                <td>20%</td>
                <td>0011</td>
                <td>山东天地红</td>
                <td>001100001130</td>
                <td>黄啸</td>
                <td>ZJXX</td>
                <td>资金信息</td>
                <td>市场编码</td>
                <td>资金信息</td>
                <td>man</td>
                <td>已提交</td>
                <td>20180124 16:07:39</td>
                <td class="more"><a href="">查看</a></td>
            </tr>
            </tbody>
        </table>
    </div>
    <!--模态框 弹出框-->
    <div class="model" style="display: none">
        <div class="header"><span>X</span></div>
        <div class="model_content">

        </div>
    </div>
</div>
<!--<script src="http://www.jq22.com/jquery/jquery-2.1.1.js"></script>-->
<script src="${contextPath}/static/asserts/js/echarts-4.0.js"></script>
<script src="${contextPath}/static/asserts/js/jquery-2.2.4.js"></script>
<script src="${contextPath}/static/js/pageCom.js"></script>
<script src="${contextPath}/static/js/chartsCom.js"></script>

<script>

    $(function () {
        $(".formFoldHandle").click(function () {
            $(".formBox").toggleClass("open")
        });

        //显示model
        $(".tableBox tbody tr td:nth-child(1) a").click(function(e){
            e.preventDefault();

            $(".model").css("display","block");

        });
        //异常model
        $(".header span").click(function (e) {
            e.preventDefault();
            $(".model").hide();
        })


    });










</script>
</body>
</html>
