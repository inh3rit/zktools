package org.inh3rit.zktools.utils;

import com.alibaba.fastjson.JSON;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Description:
 * @Author: PC-Tony
 * @Date: 21:37 2019/9/29
 */
public class ZKUtilsTest {

    @Test
    public void getZK() throws Exception {
        ZooKeeper zk = ZKUtils.getZK("47.106.143.175:2181");
        byte[] data = zk.getData("/dir3", false, null);
        System.out.println(new String(data));
//        Stat stat = zk.exists("/dir3", false);
//        System.out.println(stat);
//        List<String> children = zk.getChildren("/", false);
//        System.out.println(children);
    }

    @Test
    public void getAllChildren() throws Exception {
        ZooKeeper zk = ZKUtils.getZK("47.106.143.175:2181");
        Map<String, List> allChildren = ZKUtils.getAllChildren(zk, "/");
        System.out.println(JSON.toJSONString(allChildren));
    }

    @Test
    public void testSplit() {
        Optional<String> first = Arrays.stream("d/r/s/a/x".split("/")).findAny();
        System.out.println(first.get());

    }

    @Test
    public void testJSONFormat() {
//        String json = "{\"default\":{\"panelView\":{\"container\":\"tab\"},\"detailView\":{}},\"children\":{\"02\":{\"//\":[\"面板\",\"详情\"],\"panelView\":{\"theOrder\":[\"panel\",\"searchBox\",\"customList\"],\"detailView\":{\"panel\":{\"name\":\"panel\",\"description\":\"面板加载之后初始化布局配置\",\"layout\":\"<div id=''><\\/div>\",\"callBackAppendDom\":\"<div class='eMap-extend-panle-nav'><\\/div><div class='eMap-extend-panle-body eMap-extend-panle-body-cur'><div class='eMap-extend-panle-blank eMap-extend-city'><\\/div><\\/div><\\/div>\"},\"searchBox\":{\"name\":\"searchBox\",\"layout\":\"<div class='eMap-extend-mt15' id=''><\\/div>\",\"description\":\"搜索框\",\"//\":\"关键字搜索的字段\",\"beanTableName\":\"*\",\"option\":{\"item\":\"请输入搜索关键字\",\"container\":\"\",\"//\":\"seachbox组件需要的样式值\",\"inputArray\":[\"5\"]}},\"customList\":{\"name\":\"customList\",\"//\":[\"查询url\",\"查询条件\",\"查询结果的字段定义\",\"列表每条记录显示的详情内容html，{字段名}为占位符，从beanModel中取值\"],\"url\":\"/dataOperate/query\",\"searchData\":{\"sort\":\"OBJECTID ASC\",\"select\":\"STANDARDNAME,SOURCEDATA\",\"page\":1},\"layout\":\"<div class='ipt-group eMap-extend-mt15' id='' ><\\/div>\",\"beanModel\":[{\"name\":\"OBJECTID\"},{\"name\":\"STANDARDNAME\"},{\"name\":\"SOURCEDATA\"}],\"templet\":\"<li class='eMap-list-item' id='{OBJECTID}'><div><p>名称：<span class='eMap-txt-width'>{STANDARDNAME}<\\/span><\\/p><\\/div><div><p>地址：<span>{SOURCEDATA}<\\/span><\\/p><\\/div><\\/li>\",\"option\":{\"divId\":\"\",\"data\":{},\"pageSize\":6,\"rowClick\":\"\",\"pageShowCount\":1,\"operation\":false,\"callback\":\"\"}}}},\"detailView\":{\"//\":\"tab配置\",\"offset\":[-116,-20],\"htmlstr\":{\"tabGroup\":[{\"unique\":\"\",\"tabChange\":\"nav nav-tabs\",\"tabDiv\":\"tab-content\",\"data\":[{\"href\":\"A1-2-1_02\",\"descript\":\"基本信息\",\"id\":\"A1-2-1_02\",\"content\":\"\"}]}]},\"Data\":[{\"name\":\"STANDARDNAME\",\"value\":\"STANDARDNAME\",\"blank\":\"\"},{\"name\":\"PROVINCE\",\"value\":\"PROVINCE\",\"blank\":\"\"},{\"name\":\"CITY\",\"value\":\"CITY\",\"blank\":\"\"},{\"name\":\"COUNTY\",\"value\":\"COUNTY\",\"blank\":\"\"},{\"name\":\"TOWN\",\"value\":\"TOWN\",\"blank\":\"\"},{\"name\":\"LATITUDE\",\"value\":\"LATITUDE\",\"blank\":\"\"},{\"name\":\"LONGITUDE\",\"value\":\"LONGITUDE\",\"blank\":\"\"},{\"name\":\"STANDARDNAMECODE\",\"value\":\"STANDARDNAMECODE\",\"blank\":\"\"},{\"name\":\"USEWAY\",\"value\":\"USEWAY\",\"blank\":\"\"},{\"name\":\"ISLANDAREA\",\"value\":\"ISLANDAREA\",\"blank\":\"\"},{\"name\":\"APPROVALYEAR\",\"value\":\"APPROVALYEAR\",\"blank\":\"\"},{\"name\":\"PROJECTNAME\",\"value\":\"PROJECTNAME\",\"blank\":\"\"},{\"name\":\"TOTALAMOUNT\",\"value\":\"TOTALAMOUNT\",\"blank\":\"\"},{\"name\":\"SEAAREA\",\"value\":\"SEAAREA\",\"blank\":\"\"},{\"name\":\"DEFINITEISLAND\",\"value\":\"DEFINITEISLAND\",\"blank\":\"\"},{\"name\":\"SOURCEDATA\",\"value\":\"数据来源\",\"blank\":\"\"},{\"name\":\"POWERUSER\",\"value\":\"使用权人\",\"blank\":\"\"},{\"name\":\"SORT\",\"value\":\"排序\",\"blank\":\"\"}]}},\"03\":{\"//\":[\"面板\",\"详情\"],\"panelView\":{\"theOrder\":[\"panel\",\"searchBox\",\"customList\"],\"detailView\":{\"panel\":{\"name\":\"panel\",\"description\":\"面板加载之后初始化布局配置\",\"layout\":\"<div id=''><\\/div>\",\"callBackAppendDom\":\"<div class='eMap-extend-panle-nav'><\\/div><div class='eMap-extend-panle-body eMap-extend-panle-body-cur'><div class='eMap-extend-panle-blank eMap-extend-city'><\\/div><\\/div><\\/div>\"},\"searchBox\":{\"name\":\"searchBox\",\"layout\":\"<div class='eMap-extend-mt15' id=''><\\/div>\",\"description\":\"搜索框\",\"//\":\"关键字搜索的字段\",\"beanTableName\":\"*\",\"option\":{\"item\":\"请输入搜索关键字\",\"container\":\"\",\"//\":\"seachbox组件需要的样式值\",\"inputArray\":[\"5\"]}},\"customList\":{\"name\":\"customList\",\"//\":[\"查询url\",\"查询条件\",\"查询结果的字段定义\",\"列表每条记录显示的详情内容html，{字段名}为占位符，从beanModel中取值\"],\"url\":\"/dataOperate/query\",\"searchData\":{\"sort\":\"OBJECTID ASC\",\"select\":\"海岛名称,海岛编号\",\"page\":1},\"layout\":\"<div class='ipt-group eMap-extend-mt15' id='' ><\\/div>\",\"beanModel\":[{\"name\":\"OBJECTID\"},{\"name\":\"海岛名称\"},{\"name\":\"海岛编号\"}],\"templet\":\"<li class='eMap-list-item' id='{OBJECTID}'><div><p>名称：<span class='eMap-txt-width'>{海岛名称}<\\/span><\\/p><\\/div><div><p>地址：<span>{海岛编号}<\\/span><\\/p><\\/div><\\/li>\",\"option\":{\"divId\":\"\",\"data\":{},\"pageSize\":6,\"rowClick\":\"\",\"pageShowCount\":1,\"operation\":false,\"callback\":\"\"}}}},\"detailView\":{\"//\":\"tab配置\",\"offset\":[-116,-20],\"htmlstr\":{\"tabGroup\":[{\"unique\":\"\",\"tabChange\":\"nav nav-tabs\",\"tabDiv\":\"tab-content\",\"data\":[{\"href\":\"A1-2-1_03\",\"descript\":\"基本信息\",\"id\":\"A1-2-1_03\",\"content\":\"\"}]}]},\"Data\":[{\"name\":\"海岛名称\",\"value\":\"海岛名称\",\"blank\":\"\"},{\"name\":\"海岛编号\",\"value\":\"海岛编号\",\"blank\":\"\"},{\"name\":\"岸线编号\",\"value\":\"岸线编号\",\"blank\":\"\"},{\"name\":\"影像时相\",\"value\":\"影像时相\",\"blank\":\"\"},{\"name\":\"数据源\",\"value\":\"数据源\",\"blank\":\"\"},{\"name\":\"绘图人\",\"value\":\"绘图人\",\"blank\":\"\"},{\"name\":\"审核人\",\"value\":\"审核人\",\"blank\":\"\"},{\"name\":\"备注\",\"value\":\"备注\",\"blank\":\"\"},{\"name\":\"岸线类型\",\"value\":\"岸线类型\",\"blank\":\"\"},{\"name\":\"岸线长度\",\"value\":\"岸线长度\",\"blank\":\"\"}]}},\"04\":{\"//\":[\"面板\",\"详情\"],\"panelView\":{\"theOrder\":[\"panel\",\"searchBox\",\"customList\"],\"detailView\":{\"panel\":{\"name\":\"panel\",\"description\":\"面板加载之后初始化布局配置\",\"layout\":\"<div id=''><\\/div>\",\"callBackAppendDom\":\"<div class='eMap-extend-panle-nav'><\\/div><div class='eMap-extend-panle-body eMap-extend-panle-body-cur'><div class='eMap-extend-panle-blank eMap-extend-city'><\\/div><\\/div><\\/div>\"},\"searchBox\":{\"name\":\"searchBox\",\"layout\":\"<div class='eMap-extend-mt15' id=''><\\/div>\",\"description\":\"搜索框\",\"//\":\"关键字搜索的字段\",\"beanTableName\":\"*\",\"option\":{\"item\":\"请输入搜索关键字\",\"container\":\"\",\"//\":\"seachbox组件需要的样式值\",\"inputArray\":[\"5\"]}},\"customList\":{\"name\":\"customList\",\"//\":[\"查询url\",\"查询条件\",\"查询结果的字段定义\",\"列表每条记录显示的详情内容html，{字段名}为占位符，从beanModel中取值\"],\"url\":\"/dataOperate/query\",\"searchData\":{\"sort\":\"OBJECTID ASC\",\"select\":\"NAMECODE,DATASOURCE\",\"page\":1},\"layout\":\"<div class='ipt-group eMap-extend-mt15' id='' ><\\/div>\",\"beanModel\":[{\"name\":\"OBJECTID\"},{\"name\":\"NAMECODE\"},{\"name\":\"DATASOURCE\"}],\"templet\":\"<li class='eMap-list-item' id='{OBJECTID}'><div><p>名称：<span class='eMap-txt-width'>{NAMECODE}<\\/span><\\/p><\\/div><div><p>地址：<span>{DATASOURCE}<\\/span><\\/p><\\/div><\\/li>\",\"option\":{\"divId\":\"\",\"data\":{},\"pageSize\":6,\"rowClick\":\"\",\"pageShowCount\":1,\"operation\":false,\"callback\":\"\"}}}},\"detailView\":{\"//\":\"tab配置\",\"offset\":[-116,-20],\"htmlstr\":{\"tabGroup\":[{\"unique\":\"\",\"tabChange\":\"nav nav-tabs\",\"tabDiv\":\"tab-content\",\"data\":[{\"href\":\"A1-2-1_04\",\"descript\":\"基本信息\",\"id\":\"A1-2-1_04\",\"content\":\"\"}]}]},\"Data\":[{\"name\":\"ID\",\"value\":\"ID\",\"blank\":\"\"},{\"name\":\"REMARK\",\"value\":\"备注\",\"blank\":\"\"},{\"name\":\"AREACODE\",\"value\":\"AREACODE\",\"blank\":\"\"},{\"name\":\"NAMECODE\",\"value\":\"NAMECODE\",\"blank\":\"\"},{\"name\":\"USINGNAME\",\"value\":\"USINGNAME\",\"blank\":\"\"},{\"name\":\"STANDARDNAME\",\"value\":\"STANDARDNAME\",\"blank\":\"\"},{\"name\":\"PROVINCE\",\"value\":\"PROVINCE\",\"blank\":\"\"},{\"name\":\"CITY\",\"value\":\"CITY\",\"blank\":\"\"},{\"name\":\"COUNTY\",\"value\":\"COUNTY\",\"blank\":\"\"},{\"name\":\"TOWN\",\"value\":\"TOWN\",\"blank\":\"\"},{\"name\":\"REGISTEREDPOPULATION\",\"value\":\"REGISTEREDPOPULATION\",\"blank\":\"\"},{\"name\":\"SEAAREA\",\"value\":\"SEAAREA\",\"blank\":\"\"},{\"name\":\"FORMERNAME\",\"value\":\"FORMERNAME\",\"blank\":\"\"},{\"name\":\"PERMANENTPOPULATION\",\"value\":\"PERMANENTPOPULATION\",\"blank\":\"\"},{\"name\":\"LANDDISTANCE\",\"value\":\"LANDDISTANCE\",\"blank\":\"\"},{\"name\":\"PINYIN\",\"value\":\"PINYIN\",\"blank\":\"\"},{\"name\":\"LATITUDE\",\"value\":\"LATITUDE\",\"blank\":\"\"},{\"name\":\"LONGITUDE\",\"value\":\"LONGITUDE\",\"blank\":\"\"},{\"name\":\"MATERIALTYPE\",\"value\":\"MATERIALTYPE\",\"blank\":\"\"},{\"name\":\"VEGETATION\",\"value\":\"VEGETATION\",\"blank\":\"\"},{\"name\":\"STANDARDNAMECODE\",\"value\":\"STANDARDNAMECODE\",\"blank\":\"\"},{\"name\":\"SPECIALDEVELOPMENTTYPE\",\"value\":\"SPECIALDEVELOPMENTTYPE\",\"blank\":\"\"},{\"name\":\"GENERALDEVELOPMENTTYPE\",\"value\":\"GENERALDEVELOPMENTTYPE\",\"blank\":\"\"},{\"name\":\"ECOISLANDREEF\",\"value\":\"ECOISLANDREEF\",\"blank\":\"\"},{\"name\":\"PRELAWISLAND\",\"value\":\"PRELAWISLAND\",\"blank\":\"\"},{\"name\":\"DEFINITEISLAND\",\"value\":\"DEFINITEISLAND\",\"blank\":\"\"},{\"name\":\"STATION\",\"value\":\"STATION\",\"blank\":\"\"},{\"name\":\"ISLANDCLASS\",\"value\":\"海岛分类\",\"blank\":\"\"},{\"name\":\"SHORELINELENGTH\",\"value\":\"岸线长度\",\"blank\":\"\"},{\"name\":\"DATASOURCE\",\"value\":\"资料来源\",\"blank\":\"\"},{\"name\":\"SDE.HD_DMPCD.AREA\",\"value\":\"面积\",\"blank\":\"\"},{\"name\":\"SORT\",\"value\":\"排序\",\"blank\":\"\"}]}}}}";
        String json = "sdgsag";
        String jsonString = JSONUtils.jsonFormat(json);
        System.out.println(jsonString);
    }

    @Test
    public void testUrlDecode() throws UnsupportedEncodingException {
        System.out.println(URLDecoder.decode("This%2Bstring%2Bhas%2Bpluses", "UTF-8"));
    }

    @Test
    public void testLocalDateTime() {
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(1570630513362l / 1000, 0, ZoneOffset.of("+8"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println(localDateTime.format(formatter));
    }
}
