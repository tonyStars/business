/**
 * 登录后的index主界面加载js,重要!!!
 * description:layuimini 框架扩展
 * date:2019/11/19
 * author:Tom
 */

var muenData = [];
layui.define(["element", "jquery"], function (exports) {
    var element = layui.element,$ = layui.$,layer = layui.layer;
    // 判断是否在web容器中打开
    if (!/http(s*):\/\//.test(location.href)) {
        return layer.alert("请先将项目部署至web容器（Apache/Tomcat/Nginx/IIS/等），否则部分数据将无法显示");
    }
    layuimini = new function () {
        /**
         *  系统配置(请求的URL地址hash定位及URL后缀)
         * @param name
         * @returns {{BgColorDefault: number, urlSuffixDefault: boolean}|*}
         */
        this.config = function (name) {
            var config = {
                urlHashLocation: false,   // URL地址hash定位
                urlSuffixDefault: false, // URL后缀
                BgColorDefault: 0,       // 默认皮肤（0开始）
                checkUrlDefault: false,   // 是否判断URL有效
            };
            if (name == undefined) {
                return config;
            } else {
                return config[name];
            }
        };

        /**
         * 初始化 - index主界面调用初始化方法
         */
        this.init = function () {
            var loading = layer.load(0, {shade: false, time: 2 * 1000});
            layuimini.initBgColor();
            layuimini.initDevice();
            var menuUrl = path + "/getMenu.do";
            getMenu(menuUrl);
            var homeId = "welcome-1.html";
            var homeUrl = path + "/./plugin/layuimini/page/welcome-1.html?mpi=m-p-i-0";
            layuimini.initHome(homeId,homeUrl);
            var logoName = "Tom系统";
            var imagePath = path + '/./plugin/layuimini/images/logo.png';
            layuimini.initLogo(logoName,imagePath);
            var clearUrl = "";
            layuimini.initClear(clearUrl);
            initMenu(muenData);
            layuimini.initTab();
            layer.close(loading);
        };

        /**
         * 菜单请求接口
         * @param url 请求url
         */
        function getMenu(url) {
            $.ajax({
                type: "post",
                url: url,
                dataType: "json",
                async: false,
                cache: false,
                success: function (result) {
                    if (result.msg != undefined) {
                        layuimini.msg_error(result.msg);
                        return;
                    } else {
                        muenData = result.systemMenu;
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    layuimini.msg_error('菜单加载失败！错误码（' + XMLHttpRequest.status + '）');
                    if (XMLHttpRequest.status != 0) {
                        layuimini.msg_error('菜单加载失败！错误码（' + XMLHttpRequest.status + '）');
                        return;
                    }
                }
            });
        }

        /**
         * 初始化设备端
         */
        this.initDevice = function () {
            if (layuimini.checkMobile()) {
                $('.layuimini-tool i').attr('data-side-fold', 0);
                $('.layuimini-tool i').attr('class', 'fa fa-indent');
                $('.layui-layout-body').attr('class', 'layui-layout-body layuimini-mini');
            }
        };

        /**
         * 初始化首页信息 - 首页中的信息暂时是welcome-1.html中静态信息,后期再调用接口请求数据加载
         * @param homeId home标签的id
         * @param homeUrl home首页加载信息请求的的url
         */
        this.initHome = function (homeId,homeUrl) {
            sessionStorage.setItem('layuiminiHomeHref', homeUrl);
            $('#layuiminiHomeTabId').html('<i class="fa fa-home"></i> <span>首页</span>');
            $('#layuiminiHomeTabId').attr('lay-id', homeId);
            $('#layuiminiHomeTabIframe').html('<iframe width="100%" height="100%" frameborder="0"  src="' + homeUrl + '"></iframe>');
        };

        /**
         * 初始化logo信息
         * @param logoName logo名称
         * @param imagePath logo图片地址
         */
        this.initLogo = function (logoName,imagePath) {
            var html = '<a>\n' +
                '<img src="' + imagePath + '" alt="logo">\n' +
                '<h1> '+ logoName +'</h1>\n' +
                '</a>';
            $('.layui-layout-admin .layui-logo').html(html);
        };

        /**
         * 初始化清理缓存
         * @param url 清理缓存请求的url
         */
        this.initClear = function (url) {
            $('.layuimini-clear').attr('data-href', url);
        };

        /**
         * 初始化背景色
         */
        this.initBgColor = function () {
            var bgcolorId = sessionStorage.getItem('layuiminiBgcolorId');
            if (bgcolorId == null || bgcolorId == undefined || bgcolorId == '') {
                bgcolorId = layuimini.config('BgColorDefault');
            }
            var bgcolorData = layuimini.bgColorConfig(bgcolorId);
            var styleHtml = '.layui-layout-admin .layui-header{background-color:' + bgcolorData.headerRight + '!important;}\n' +
                '.layui-header>ul>.layui-nav-item.layui-this,.layuimini-tool i:hover{background-color:' + bgcolorData.headerRightThis + '!important;}\n' +
                '.layui-layout-admin .layui-logo {background-color:' + bgcolorData.headerLogo + '!important;}\n' +
                '.layui-side.layui-bg-black,.layui-side.layui-bg-black>.layui-left-menu>ul {background-color:' + bgcolorData.menuLeft + '!important;}\n' +
                '.layui-left-menu .layui-nav .layui-nav-child a:hover:not(.layui-this) {background-color:' + bgcolorData.menuLeftHover + ';}\n' +
                '.layui-layout-admin .layui-nav-tree .layui-this, .layui-layout-admin .layui-nav-tree .layui-this>a, .layui-layout-admin .layui-nav-tree .layui-nav-child dd.layui-this, .layui-layout-admin .layui-nav-tree .layui-nav-child dd.layui-this a {\n' +
                '    background-color: ' + bgcolorData.menuLeftThis + ' !important;\n' +
                '}';
            $('#layuimini-bg-color').html(styleHtml);
        };

        /**
         * 初始化菜单栏 - 该方法之渲染最多三级菜单
         * @param data 接口返回的菜单参数
         */
        function initMenu(data) {
            /**
             * 顶层菜单(不断遍历累加)
             * @type {string}
             */
            var headerMenuHtml = '',
                /**
                 * 手持顶层菜单(不断遍历累加)
                 * @type {string}
                 */
                headerMobileMenuHtml = '',
                /**
                 * 左侧菜单(不断遍历累加)
                 * @type {string}
                 */
                leftMenuHtml = '',
                headerMenuCheckDefault = 'layui-this',
                leftMenuCheckDefault = 'layui-this';
            window.menuParameId = 1;
            /**遍历菜单集合 key从0开始,val第一层集合*/
            $.each(data, function (key, val) {
                /**加载顶层菜单,依次遍历顶层所有菜单 通过 "+=" 拼接,最终得到所有的顶层菜单headerMenuHtml*/
                headerMenuHtml += '<li class="layui-nav-item ' + headerMenuCheckDefault + '" id="' + key + 'HeaderId" data-menu="' + key + '"> <a style="cursor: pointer"><i class="' + val.icon + '"></i> ' + val.title + '</a> </li>\n';
                /**加载手持顶层菜单,依次遍历手持顶层所有菜单 通过 "+=" 拼接,最终得到所有的手持顶层菜单headerMobileMenuHtml*/
                headerMobileMenuHtml += '<dd><a style="cursor: pointer" id="' + key + 'HeaderId" data-menu="' + key + '"><i class="' + val.icon + '"></i> ' + val.title + '</a></dd>\n';
                /**设置左侧菜单,以树形结构展示*/
                leftMenuHtml += '<ul class="layui-nav layui-nav-tree layui-left-nav-tree ' + leftMenuCheckDefault + '" id="' + key + '">\n';
                /**获取顶层菜单的下级菜单,即左侧父菜单*/
                var menuList = val.children;
                /**遍历每个顶层菜单的下级菜单集合,index从0开始遍历,menu为集合中每个对象参数*/
                $.each(menuList, function (index, menu) {
                    leftMenuHtml += '<li class="layui-nav-item">\n';
                    /**如果左侧主菜单还存在下级菜单,则创建该菜单的下级菜单buildChildHtml的加载方法*/
                    if (menu.children != undefined && menu.children != []) {
                        leftMenuHtml += '<a style="cursor: pointer" class="layui-menu-tips" ><i class="' + menu.icon + '"></i><span class="layui-left-nav"> ' + menu.title + '</span> </a>';
                        /**创建一个加载三级菜单的方法*/
                        var buildChildHtml = function (html, child, menuParameId) {
                            html += '<dl class="layui-nav-child">\n';
                            $.each(child, function (childIndex, childMenu) {
                                html += '<dd>\n';
                                if (childMenu.children != undefined && childMenu.children != []) {
                                    html += '<a style="cursor: pointer" class="layui-menu-tips" ><i class="' + childMenu.icon + '"></i><span class="layui-left-nav"> ' + childMenu.title + '</span></a>';
                                    html = buildChildHtml(html, childMenu.children, menuParameId);
                                } else {
                                    html += '<a style="cursor: pointer" class="layui-menu-tips" data-type="tabAdd"  data-tab-mpi="m-p-i-' + menuParameId + '" data-tab="' + childMenu.href + '" target="' + childMenu.target + '"><i class="' + childMenu.icon + '"></i><span class="layui-left-nav"> ' + childMenu.title + '</span></a>\n';
                                    menuParameId++;
                                    window.menuParameId = menuParameId;
                                }
                                html += '</dd>\n';
                            });
                            html += '</dl>\n';
                            return html;
                        };
                        /**最后加载左侧菜单及其子菜单(即三级菜单)*/
                        leftMenuHtml = buildChildHtml(leftMenuHtml, menu.children, menuParameId);
                    } else {
                        leftMenuHtml += '<a style="cursor: pointer" class="layui-menu-tips"  data-type="tabAdd" data-tab-mpi="m-p-i-' + menuParameId + '" data-tab="' + menu.href + '" target="' + menu.target + '"><i class="' + menu.icon + '"></i><span class="layui-left-nav"> ' + menu.title + '</span></a>\n';
                        menuParameId++;
                    }
                    leftMenuHtml += '</li>\n';
                });
                leftMenuHtml += '</ul>\n';
                headerMenuCheckDefault = '';
                leftMenuCheckDefault = 'layui-hide';
            });
            /**电脑PC端*/
            $('.layui-header-pc-menu').html(headerMenuHtml);
            /**手机或手持终端*/
            $('.layui-header-mini-menu').html(headerMobileMenuHtml);
            $('.layui-left-menu').html(leftMenuHtml);
            element.init();
        };

        /**
         * 初始化选项卡
         */
        this.initTab = function () {
            var locationHref = window.location.href;
            var urlArr = locationHref.split("#");
            if (urlArr.length >= 2) {
                var href = urlArr.pop();
                /**判断链接是否有效*/
                var checkUrl = layuimini.checkUrl(href);
                if (checkUrl != true) {
                    return layuimini.msg_error(checkUrl);
                }
                /**判断tab是否存在*/
                var checkTab = layuimini.checkTab(href);
                if (!checkTab) {
                    var title = href,
                        tabId = href;
                    $("[data-tab]").each(function () {
                        var checkHref = $(this).attr("data-tab");
                        /**判断是否带参数了*/
                        if (layuimini.config('urlSuffixDefault')) {
                            if (href.indexOf("mpi=") > -1) {
                                var menuParameId = $(this).attr('data-tab-mpi');
                                if (checkHref.indexOf("?") > -1) {
                                    checkHref = checkHref + '&mpi=' + menuParameId;
                                } else {
                                    checkHref = checkHref + '?mpi=' + menuParameId;
                                }
                            }
                        }
                        if (checkHref == tabId) {
                            title = $(this).html();
                            title = title.replace('style="display: none;"', '');
                            /**自动展开菜单栏*/
                            var addMenuClass = function ($element, type) {
                                if (type == 1) {
                                    $element.addClass('layui-this');
                                    if ($element.attr('class') != 'layui-nav-item layui-this') {
                                        addMenuClass($element.parent().parent(), 2);
                                    } else {
                                        var moduleId = $element.parent().attr('id');
                                        $(".layui-header-menu li").attr('class', 'layui-nav-item');
                                        $("#" + moduleId + "HeaderId").addClass("layui-this");
                                        $(".layui-left-nav-tree").attr('class', 'layui-nav layui-nav-tree layui-hide');
                                        $("#" + moduleId).attr('class', 'layui-nav layui-nav-tree layui-this');
                                    }
                                } else {
                                    $element.addClass('layui-nav-itemed');
                                    if ($element.attr('class') != 'layui-nav-item layui-nav-itemed') {
                                        addMenuClass($element.parent().parent(), 2);
                                    } else {
                                        var moduleId = $element.parent().attr('id');
                                        $(".layui-header-menu li").attr('class', 'layui-nav-item');
                                        $("#" + moduleId + "HeaderId").addClass("layui-this");
                                        $(".layui-left-nav-tree").attr('class', 'layui-nav layui-nav-tree layui-hide');
                                        $("#" + moduleId).attr('class', 'layui-nav layui-nav-tree layui-this');
                                    }
                                }
                            };
                            addMenuClass($(this).parent(), 1);
                        }
                    });
                    var layuiminiHomeTab = $('#layuiminiHomeTab').attr('lay-id'),
                        layuiminiHomeHref = sessionStorage.getItem('layuiminiHomeHref');
                    /**非菜单打开的tab窗口*/
                    if (href == title) {
                        var layuiminiTabInfo = JSON.parse(sessionStorage.getItem("layuiminiTabInfo"));
                        if (layuiminiTabInfo != null) {
                            var check = layuiminiTabInfo[tabId];
                            if (check != undefined || check != null) {
                                title = check['title'];
                            }
                        }
                    }
                    if (layuiminiHomeTab != href && layuiminiHomeHref != href) {
                        layuimini.addTab(tabId, href, title, true);
                        layuimini.changeTab(tabId);
                    }
                }
            }
            if (layuimini.config('urlHashLocation')) {
                layuimini.hashTab();
            }
        };

        /**
         * 配色方案配置项(默认选中第一个方案)
         * @param bgcolorId
         */
        this.bgColorConfig = function (bgcolorId) {
            var bgColorConfig = [
                {
                    headerRight: '#001529',//上面头部除菜单部分底色
                    headerRightThis: '#185794',//上面头部分菜单底色
                    headerLogo: '#001529',//上左边LOGO部分底色
                    menuLeft: '#001529',//左侧菜单栏目底色
                    menuLeftThis: '#185794',//鼠标点击菜单选择之后的颜色
                    menuLeftHover: '#1890ff',//鼠标挪动在菜单上的颜色
                },
                {
                    headerRight: '#1aa094',
                    headerRightThis: '#197971',
                    headerLogo: '#243346',
                    menuLeft: '#2f4056',
                    menuLeftThis: '#1aa094',
                    menuLeftHover: '#3b3f4b',
                },
                {
                    headerRight: '#23262e',
                    headerRightThis: '#0c0c0c',
                    headerLogo: '#0c0c0c',
                    menuLeft: '#23262e',
                    menuLeftThis: '#185794',
                    menuLeftHover: '#1890ff',
                },
                {
                    headerRight: '#1E5B94',
                    headerRightThis: '#ab5f83',
                    headerLogo: '#1E5B94',
                    menuLeft: '#1E5B94',
                    menuLeftThis: '#ab5f83',
                    menuLeftHover: '#0e90d2',
                },
                {
                    headerRight: '#1aa094',
                    headerRightThis: '#197971',
                    headerLogo: '#0c0c0c',
                    menuLeft: '#23262e',
                    menuLeftThis: '#1aa094',
                    menuLeftHover: '#3b3f4b',
                },
                {
                    headerRight: '#1e9fff',
                    headerRightThis: '#0069b7',
                    headerLogo: '#0c0c0c',
                    menuLeft: '#1f1f1f',
                    menuLeftThis: '#1aa094',
                    menuLeftHover: '#3b3f4b',
                },

                {
                    headerRight: '#ffb800',
                    headerRightThis: '#d09600',
                    headerLogo: '#243346',
                    menuLeft: '#2f4056',
                    menuLeftThis: '#1aa094',
                    menuLeftHover: '#3b3f4b',
                },
                {
                    headerRight: '#e82121',
                    headerRightThis: '#ae1919',
                    headerLogo: '#0c0c0c',
                    menuLeft: '#1f1f1f',
                    menuLeftThis: '#1aa094',
                    menuLeftHover: '#3b3f4b',
                },
                {
                    headerRight: '#963885',
                    headerRightThis: '#772c6a',
                    headerLogo: '#243346',
                    menuLeft: '#2f4056',
                    menuLeftThis: '#1aa094',
                    menuLeftHover: '#3b3f4b',
                },
                {
                    headerRight: '#1e9fff',
                    headerRightThis: '#0069b7',
                    headerLogo: '#0069b7',
                    menuLeft: '#1f1f1f',
                    menuLeftThis: '#1aa094',
                    menuLeftHover: '#3b3f4b',
                },
                {
                    headerRight: '#ffb800',
                    headerRightThis: '#d09600',
                    headerLogo: '#d09600',
                    menuLeft: '#2f4056',
                    menuLeftThis: '#1aa094',
                    menuLeftHover: '#3b3f4b',
                },
                {
                    headerRight: '#e82121',
                    headerRightThis: '#ae1919',
                    headerLogo: '#d91f1f',
                    menuLeft: '#1f1f1f',
                    menuLeftThis: '#1aa094',
                    menuLeftHover: '#3b3f4b',
                }
            ];
            if (bgcolorId == undefined) {
                return bgColorConfig;
            } else {
                return bgColorConfig[bgcolorId];
            }
        };

        /**
         * 构建背景颜色选择
         * @returns {string}
         */
        this.buildBgColorHtml = function () {
            var html = '';
            var bgcolorId = sessionStorage.getItem('layuiminiBgcolorId');
            if (bgcolorId == null || bgcolorId == undefined || bgcolorId == '') {
                bgcolorId = 0;
            }
            var bgColorConfig = layuimini.bgColorConfig();
            $.each(bgColorConfig, function (key, val) {
                if (key == bgcolorId) {
                    html += '<li class="layui-this" data-select-bgcolor="' + key + '">\n';
                } else {
                    html += '<li  data-select-bgcolor="' + key + '">\n';
                }
                html += '<a data-skin="skin-blue" style="cursor: pointer" class="clearfix full-opacity-hover">\n' +
                    '<div><span style="display:block; width: 20%; float: left; height: 12px; background: ' + val.headerLogo + ';"></span><span style="display:block; width: 80%; float: left; height: 12px; background: ' + val.headerRight + ';"></span></div>\n' +
                    '<div><span style="display:block; width: 20%; float: left; height: 40px; background: ' + val.menuLeft + ';"></span><span style="display:block; width: 80%; float: left; height: 40px; background: #f4f5f7;"></span></div>\n' +
                    '</a>\n' +
                    '</li>';
            });
            return html;
        };

        /**
         * 判断窗口是否已打开
         * @param tabId
         **/
        this.checkTab = function (tabId, isIframe) {
            /**判断选项卡上是否有*/
            var checkTab = false;
            if (isIframe == undefined || isIframe == false) {
                $(".layui-tab-title li").each(function () {
                    checkTabId = $(this).attr('lay-id');
                    if (checkTabId != null && checkTabId == tabId) {
                        checkTab = true;
                    }
                });
            } else {
                parent.layui.$(".layui-tab-title li").each(function () {
                    checkTabId = $(this).attr('lay-id');
                    if (checkTabId != null && checkTabId == tabId) {
                        checkTab = true;
                    }
                });
            }
            if (checkTab == false) {
                return false;
            }
            /**判断sessionStorage是否有*/
            var layuiminiTabInfo = JSON.parse(sessionStorage.getItem("layuiminiTabInfo"));
            if (layuiminiTabInfo == null) {
                layuiminiTabInfo = {};
            }
            var check = layuiminiTabInfo[tabId];
            if (check == undefined || check == null) {
                return false;
            }
            return true;
        };

        /**
         * 打开新窗口
         * @param tabId
         * @param href
         * @param title
         */
        this.addTab = function (tabId, href, title, addSession) {
            if (addSession == undefined || addSession == true) {
                var layuiminiTabInfo = JSON.parse(sessionStorage.getItem("layuiminiTabInfo"));
                if (layuiminiTabInfo == null) {
                    layuiminiTabInfo = {};
                }
                layuiminiTabInfo[tabId] = {href: href, title: title}
                sessionStorage.setItem("layuiminiTabInfo", JSON.stringify(layuiminiTabInfo));
            }
            element.tabAdd('layuiminiTab', {
                title: title + '<i data-tab-close="" class="layui-icon layui-unselect layui-tab-close">ဆ</i>' //用于演示
                , content: '<iframe width="100%" height="100%" frameborder="0"  src="' + href + '"></iframe>'
                , id: tabId
            });
        };

        /**
         * 删除窗口
         * @param tabId
         */
        this.delTab = function (tabId) {
            var layuiminiTabInfo = JSON.parse(sessionStorage.getItem("layuiminiTabInfo"));
            if (layuiminiTabInfo != null) {
                delete layuiminiTabInfo[tabId];
                sessionStorage.setItem("layuiminiTabInfo", JSON.stringify(layuiminiTabInfo))
            }
            element.tabDelete('layuiminiTab', tabId);
        };

        /**
         * 切换选项卡
         **/
        this.changeTab = function (tabId) {
            element.tabChange('layuiminiTab', tabId);
        };

        /**
         * Hash地址的定位
         */
        this.hashTab = function () {
            var layId = location.hash.replace(/^#/, '');
            element.tabChange('layuiminiTab', layId);
            element.on('tab(layuiminiTab)', function (elem) {
                location.hash = $(this).attr('lay-id');
            });
        };

        /**
         * 判断是否为手机
         */
        this.checkMobile = function () {
            var ua = navigator.userAgent.toLocaleLowerCase();
            var pf = navigator.platform.toLocaleLowerCase();
            var isAndroid = (/android/i).test(ua) || ((/iPhone|iPod|iPad/i).test(ua) && (/linux/i).test(pf))
                || (/ucweb.*linux/i.test(ua));
            var isIOS = (/iPhone|iPod|iPad/i).test(ua) && !isAndroid;
            var isWinPhone = (/Windows Phone|ZuneWP7/i).test(ua);
            var clientWidth = document.documentElement.clientWidth;
            if (!isAndroid && !isIOS && !isWinPhone && clientWidth > 768) {
                return false;
            } else {
                return true;
            }
        };

        /**
         * 判断链接是否有效
         * @param url
         * @returns {boolean}
         */
        this.checkUrl = function (url) {
            if (!layuimini.config('checkUrlDefault')) {
                return true;
            }
            var msg = true;
            $.ajax({
                url: url,
                type: 'get',
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                async: false,
                error: function (xhr, textstatus, thrown) {
                    msg = 'Status:' + xhr.status + '，' + xhr.statusText + '，请稍后再试！';
                }
            });
            return msg;
        };

        /**
         * 成功
         * @param title
         * @returns {*}
         */
        this.msg_success = function (title) {
            return layer.msg(title, {icon: 1, shade: this.shade, scrollbar: false, time: 2000, shadeClose: true});
        };

        /**
         * 失败
         * @param title
         * @returns {*}
         */
        this.msg_error = function (title) {
            return layer.msg(title, {icon: 2, shade: this.shade, scrollbar: false, time: 3000, shadeClose: true});
        };

        /**
         * 选项卡滚动
         */
        this.tabRoll = function () {
            $(window).on("resize", function (event) {
                var topTabsBox = $("#top_tabs_box"),
                    topTabsBoxWidth = $("#top_tabs_box").width(),
                    topTabs = $("#top_tabs"),
                    topTabsWidth = $("#top_tabs").width(),
                    tabLi = topTabs.find("li.layui-this"),
                    top_tabs = document.getElementById("top_tabs"),
                    event = event || window.event;

                if (topTabsWidth > topTabsBoxWidth) {
                    if (tabLi.position().left > topTabsBoxWidth || tabLi.position().left + topTabsBoxWidth > topTabsWidth) {
                        topTabs.css("left", topTabsBoxWidth - topTabsWidth);
                    } else {
                        topTabs.css("left", -tabLi.position().left);
                    }
                    /**拖动效果*/
                    var flag = false;
                    var cur = {
                        x: 0,
                        y: 0
                    }
                    var nx, dx, x;

                    function down(event) {
                        flag = true;
                        var touch;
                        if (event.touches) {
                            touch = event.touches[0];
                        } else {
                            touch = event;
                        }
                        cur.x = touch.clientX;
                        dx = top_tabs.offsetLeft;
                    }

                    function move(event) {
                        var self = this;
                        if (flag) {
                            window.getSelection ? window.getSelection().removeAllRanges() : document.selection.empty();
                            var touch;
                            if (event.touches) {
                                touch = event.touches[0];
                            } else {
                                touch = event;
                            }
                            nx = touch.clientX - cur.x;
                            x = dx + nx;
                            if (x > 0) {
                                x = 0;
                            } else {
                                if (x < topTabsBoxWidth - topTabsWidth) {
                                    x = topTabsBoxWidth - topTabsWidth;
                                } else {
                                    x = dx + nx;
                                }
                            }
                            top_tabs.style.left = x + "px";
                            //阻止页面的滑动默认事件
                            document.addEventListener("touchmove", function () {
                                event.preventDefault();
                            }, false);
                        }
                    }

                    //鼠标释放时候的函数
                    function end() {
                        flag = false;
                    }

                    //pc端拖动效果
                    topTabs.on("mousedown", down);
                    topTabs.on("mousemove", move);
                    $(document).on("mouseup", end);
                    //移动端拖动效果
                    topTabs.on("touchstart", down);
                    topTabs.on("touchmove", move);
                    topTabs.on("touchend", end);
                } else {
                    //移除pc端拖动效果
                    topTabs.off("mousedown", down);
                    topTabs.off("mousemove", move);
                    topTabs.off("mouseup", end);
                    //移除移动端拖动效果
                    topTabs.off("touchstart", down);
                    topTabs.off("touchmove", move);
                    topTabs.off("touchend", end);
                    topTabs.removeAttr("style");
                    return false;
                }
            }).resize();
        };


    };

    /**
     * 关闭选项卡
     **/
    $('body').on('click', '[data-tab-close]', function () {
        var loading = layer.load(0, {shade: false, time: 2 * 1000});
        $parent = $(this).parent();
        tabId = $parent.attr('lay-id');
        if (tabId != undefined || tabId != null) {
            layuimini.delTab(tabId);
        }
        layuimini.tabRoll();
        layer.close(loading);
    });

    /**
     * 打开新窗口
     */
    $('body').on('click', '[data-tab]', function () {
        var loading = layer.load(0, {shade: false, time: 2 * 1000});
        var tabId = $(this).attr('data-tab'),
            href = $(this).attr('data-tab'),
            title = $(this).html(),
            target = $(this).attr('target');
        if (target == '_blank') {
            layer.close(loading);
            window.open(href, "_blank");
            return false;
        }
        title = title.replace('style="display: none;"', '');

        // 拼接参数
        if (layuimini.config('urlSuffixDefault')) {
            var menuParameId = $(this).attr('data-tab-mpi');
            if (href.indexOf("?") > -1) {
                href = href + '&mpi=' + menuParameId;
                tabId = href;
            } else {
                href = href + '?mpi=' + menuParameId;
                tabId = href;
            }
        }

        // 判断链接是否有效
        var checkUrl = layuimini.checkUrl(href);
        if (checkUrl != true) {
            return layuimini.msg_error(checkUrl);
        }

        if (tabId == null || tabId == undefined) {
            tabId = new Date().getTime();
        }
        // 判断该窗口是否已经打开过
        var checkTab = layuimini.checkTab(tabId);
        if (!checkTab) {
            layuimini.addTab(tabId, href, title, true);
        }
        element.tabChange('layuiminiTab', tabId);
        layuimini.initDevice();
        layuimini.tabRoll();
        layer.close(loading);
    });

    /**
     * 在iframe子菜单上打开新窗口
     */
    $('body').on('click', '[data-iframe-tab]', function () {
        var loading = parent.layer.load(0, {shade: false, time: 2 * 1000});
        var tabId = $(this).attr('data-iframe-tab'),
            href = $(this).attr('data-iframe-tab'),
            icon = $(this).attr('data-icon'),
            title = $(this).attr('data-title'),
            target = $(this).attr('target');
        if (target == '_blank') {
            parent.layer.close(loading);
            window.open(href, "_blank");
            return false;
        }
        title = '<i class="' + icon + '"></i><span class="layui-left-nav"> ' + title + '</span>';
        if (tabId == null || tabId == undefined) {
            tabId = new Date().getTime();
        }
        // 判断该窗口是否已经打开过
        var checkTab = layuimini.checkTab(tabId, true);
        if (!checkTab) {
            var layuiminiTabInfo = JSON.parse(sessionStorage.getItem("layuiminiTabInfo"));
            if (layuiminiTabInfo == null) {
                layuiminiTabInfo = {};
            }
            layuiminiTabInfo[tabId] = {href: href, title: title}
            sessionStorage.setItem("layuiminiTabInfo", JSON.stringify(layuiminiTabInfo));
            parent.layui.element.tabAdd('layuiminiTab', {
                title: title + '<i data-tab-close="" class="layui-icon layui-unselect layui-tab-close">ဆ</i>' //用于演示
                , content: '<iframe width="100%" height="100%" frameborder="0"  src="' + href + '"></iframe>'
                , id: tabId
            });
        }
        parent.layui.element.tabChange('layuiminiTab', tabId);
        layuimini.tabRoll();
        parent.layer.close(loading);
    });

    /**
     * 左侧菜单的切换
     */
    $('body').on('click', '[data-menu]', function () {
        var loading = layer.load(0, {shade: false, time: 2 * 1000});
        $parent = $(this).parent();
        menuId = $(this).attr('data-menu');
        // header
        $(".layui-header-menu .layui-nav-item.layui-this").removeClass('layui-this');
        $(this).addClass('layui-this');
        // left
        $(".layui-left-menu .layui-nav.layui-nav-tree.layui-this").addClass('layui-hide');
        $(".layui-left-menu .layui-nav.layui-nav-tree.layui-this.layui-hide").removeClass('layui-this');
        $("#" + menuId).removeClass('layui-hide');
        $("#" + menuId).addClass('layui-this');
        layer.close(loading);
    });

    /**
     * 清理
     */
    $('body').on('click', '[data-clear]', function () {
        var loading = layer.load(0, {shade: false, time: 2 * 1000});
        sessionStorage.clear();

        // 判断是否清理服务端
        var clearUrl = $(this).attr('data-href');
        if (clearUrl != undefined && clearUrl != '' && clearUrl != null) {
            $.getJSON(clearUrl, function (data, status) {
                layer.close(loading);
                if (data.code != 1) {
                    return layuimini.msg_error(data.msg);
                } else {
                    return layuimini.msg_success(data.msg);
                }
            }).fail(function () {
                layer.close(loading);
                return layuimini.msg_error('清理缓存接口有误');
            });
        } else {
            layer.close(loading);
            return layuimini.msg_success('清除缓存成功');
        }
    });

    /**
     * 刷新
     */
    $('body').on('click', '[data-refresh]', function () {
        $(".layui-tab-item.layui-show").find("iframe")[0].contentWindow.location.reload();
        layuimini.msg_success('刷新成功');
    });

    /**
     * 选项卡操作
     */
    $('body').on('click', '[data-page-close]', function () {
        var loading = layer.load(0, {shade: false, time: 2 * 1000});
        var closeType = $(this).attr('data-page-close');
        $(".layui-tab-title li").each(function () {
            tabId = $(this).attr('lay-id');
            var id = $(this).attr('id');
            if (id != 'layuiminiHomeTabId') {
                var tabClass = $(this).attr('class');
                if (closeType == 'all') {
                    layuimini.delTab(tabId);
                } else {
                    if (tabClass != 'layui-this') {
                        layuimini.delTab(tabId);
                    }
                }
            }
        });
        layuimini.tabRoll();
        layer.close(loading);
    });

    /**
     * 菜单栏缩放
     */
    $('body').on('click', '[data-side-fold]', function () {
        var loading = layer.load(0, {shade: false, time: 2 * 1000});
        var isShow = $(this).attr('data-side-fold');
        if (isShow == 1) { // 缩放
            $(this).attr('data-side-fold', 0);
            $('.layuimini-tool i').attr('class', 'fa fa-indent');
            $('.layui-layout-body').attr('class', 'layui-layout-body layuimini-mini');
        } else { // 正常
            $(this).attr('data-side-fold', 1);
            $('.layuimini-tool i').attr('class', 'fa fa-outdent');
            $('.layui-layout-body').attr('class', 'layui-layout-body layuimini-all');
        }
        layuimini.tabRoll();
        element.init();
        layer.close(loading);
    });

    /**
     * 监听提示信息
     */
    $("body").on("mouseenter", ".layui-menu-tips", function () {
        var classInfo = $(this).attr('class'),
            tips = $(this).children('span').text(),
            isShow = $('.layuimini-tool i').attr('data-side-fold');
        if (isShow == 0) {
            openTips = layer.tips(tips, $(this), {tips: [2, '#2f4056'], time: 30000});
        }
    });
    $("body").on("mouseleave", ".layui-menu-tips", function () {
        var isShow = $('.layuimini-tool i').attr('data-side-fold');
        if (isShow == 0) {
            try {
                layer.close(openTips);
            } catch (e) {
                console.log(e.message);
            }
        }
    });

    /**
     * 弹出配色方案
     */
    $('body').on('click', '[data-bgcolor]', function () {
        var loading = layer.load(0, {shade: false, time: 2 * 1000});
        var clientHeight = (document.documentElement.clientHeight) - 95;
        var bgColorHtml = layuimini.buildBgColorHtml();
        var html = '<div class="layuimini-color">\n' +
            '<div class="color-title">\n' +
            '<span>配色方案</span>\n' +
            '</div>\n' +
            '<div class="color-content">\n' +
            '<ul>\n' + bgColorHtml + '</ul>\n' +
            '</div>\n' +
            '</div>';
        layer.open({
            type: 1,
            title: false,
            closeBtn: 0,
            shade: 0.2,
            anim: 2,
            shadeClose: true,
            id: 'layuiminiBgColor',
            area: ['340px', clientHeight + 'px'],
            offset: 'rb',
            content: html,
            end:function () {
                $('.layuimini-select-bgcolor').removeClass('layui-this');
            }
        });
        layer.close(loading);
    });

    /**
     * 选择配色方案
     */
    $('body').on('click', '[data-select-bgcolor]', function () {
        var bgcolorId = $(this).attr('data-select-bgcolor');
        $('.layuimini-color .color-content ul .layui-this').attr('class', '');
        $(this).attr('class', 'layui-this');
        sessionStorage.setItem('layuiminiBgcolorId', bgcolorId);
        layuimini.initBgColor();
    });

    exports("layuimini", layuimini);
});
