# 使用方式

1. 添加依赖

2. 创建相关的类文件
    * XXXSort或XXXFilter与XXXConfigItem
        XXXSort与XXXFilter主要定于有什么类型的配置，与真正执行Sort/Filter 。
        XXXConfigItem 主要完成配置的保存工作。一般来说添加的配置字段是相同的。
    * 配置的展示由客户端定义，需要定义相应的ViewHolder，并且需要实现方法`bind(XXX)`
        viewHolder需要使用`container`类的`frameLayout`字段上。
    * 创建相应的Factory，需要根据ViewType返回相应的viewHolder。
3. 使用
    * 添加自定义的XXXFilter/XXXSort

        ```java
            List<FilterChain<ApplicationItem>> sortList = new ArrayList<>();
            sortList.add(new PackageFilter(null, null));
            sortList.add(new NameFilter(null, null));
            sortList.add(new DateFilter("安装时间", null, null, null));
            sortList.add(new UpdateTimeFilter("更新次数", null, 0, 0, false, false));
        ```

    * 实例化

    ```java
    filterDialog = new FilterDialog<>(this, sortList, new ApplicationFilterFactory());
    ```

    * 创建对象`RuntimeTypeAdapterFactory`

    * 初始化

        ```java
        filterDialog.init("filter", adapterFactory, configRuntimeTypeAdapterFactory);
        ```

    * 设置监听事件`setListener`
        `onSaveState`(保存数据时)与`onInitHistory`（将json文件中的内容显示到界面上）
        
    * 在合适的时机调用`filterDialog.show()`
    
## 注意

1. 数据使用Json保存，保存位置为当前包名/config_editor_${自定义的补充信息}.json

2. 默认添加了Filter 组件包括`时间区间`，`double值区间`，`正则判断`。分别是DateRangeFilter，ValueRangeFilter，RegexpFilter。
    对应的实现是SimpleXXX