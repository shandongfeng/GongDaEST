## 项目简介

工大二手堂项目（仿咸鱼）包含从首页浏览商品，商品详情，微信登录，加入购物车，提交订单，微信支付，订单管理等功能。

### 技术栈

✨ 脚手架工具：vue-cli
🔥 前端框架：[uni-app](https://uniapp.dcloud.net.cn/) (Vue3 + TS + Setup)
🍍 状态管理器：vue3 新秀 Pinia，犹如 react zustand 般的体验，友好的 api 和异步处理
🏆 开发语言：政治正确 TypeScript
🎉UI 组件：[uni-ui](https://uniapp.dcloud.net.cn/component/uniui/uni-ui.html)
🎨css 样式：scss
🔒 权限管理：页面级、菜单级、按钮级、接口级
✊ 状态管理：[pinia](https://pinia.vuejs.org/zh/)
💪 组件按需导入：main.js 手动导入

📖 代码工具：VSCode + 微信开发者工具+IDEA+MySQL



## 资料说明

### 📀 视频学习

#### 			1.原生开发：

 	开发软件：微信开发者工具（开放端口，导入项目路径）
 	
 	教学视频：https://www.bilibili.com/video/BV1834y1676P?p=7&vd_source=80fc182783ecfbd4e3ed3d35d34b18ba

#### 			2.uniapp 框架开发

 	开发软件：HBuilder X（过渡）或者 vscode（主力）
 	
 	教学视频：https://www.bilibili.com/video/BV1Bp4y1379L?p=11&vd_source=80fc182783ecfbd4e3ed3d35d34b18ba

### 📗 接口文档

​		[https://www.apifox.cn/apidoc/shared-0e6ee326-d646-41bd-9214-29dbf47648fa/](https://www.apifox.cn/apidoc/shared-0e6ee326-d646-41bd-9214-29dbf47648fa/)

### ✏️ 在线笔记

​		https://github.com/shandongfeng/GongDaEST/tree/master/%E6%88%91%E7%9A%84%E7%AC%94%E8%AE%B0

### 📖学习手册

​		微信开发文档：https://developers.weixin.qq.com/miniprogram/dev/framework/

​		uniapp开发文档：https://uniapp.dcloud.net.cn/quickstart.html#

### 📦 项目源码

​		https://github.com/shandongfeng/GongDaEST



## 项目运行

```
前端：
	1.下载小程序前端——AppletFrontend代码，直接用vscode打开
	2.将项目AppletFrontend\dist\dev\mp-weixin导入微信开发者工具
	3.vscode终端输入: npm run dev:mp-weixin
后端：
	1.将AppletBackend文件夹直接用idea打开即可
	2.运行src\main\java\com\fengxiangkai\AppletBackend.java即可
```



## 项目创建参考（两种）

### 	一.  HBuilder X  +  微信开发者工具

#### 		1.微信原生开发

```
安装教程：
https://blog.csdn.net/qq_62898618/article/details/128513692?ops_request_misc=&request_id=&biz_id=102&utm_term=%E5%BE%AE%E4%BF%A1%E5%B0%8F%E7%A8%8B%E5%BA%8F&utm_medium=distribute.pc_search_result.none-task-blog-2~all~sobaiduweb~default-0-128513692.142^v94^control&spm=1018.2226.3001.4187
```

#### 		2.Uniapp开发

```
安装教程：
https://www.bilibili.com/video/BV1Bp4y1379L?p=2&vd_source=80fc182783ecfbd4e3ed3d35d34b18ba

关于命令行创建uniapp项目（因为视频里的文件资源失效等问题，导致无法创建）
视频文件资源: https://github.com/shandongfeng/GongDaEST/tree/master/%E9%A1%B9%E7%9B%AE%E4%BE%9D%E8%B5%96
两种解决：
1.创建项目的文件目录下打开命令行，输入：vue create -p uni-preset-vue 项目名
2.直接使用上方链接下载的小兔仙ts模板，下载即用
```

#### 	二. VSCode  +  微信开发者工具

#### 		1.微信原生开发

```
安装教程
https://blog.csdn.net/qq_55342245/article/details/115444815?ops_request_misc=%257B%2522request%255Fid%2522%253A%2522169649470916800226556146%2522%252C%2522scm%2522%253A%252220140713.130102334..%2522%257D&request_id=169649470916800226556146&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~all~top_positive~default-1-115444815-null-null.142^v94^control&utm_term=vscode%E5%BC%80%E5%8F%91%E5%BE%AE%E4%BF%A1%E5%B0%8F%E7%A8%8B%E5%BA%8F&spm=1018.2226.3001.4187

下载完插件，将创建的项目导入即可
```

#### 		2.Uniapp开发

```
安装教程：
https://blog.csdn.net/qq_55342245/article/details/115444815?ops_request_misc=%257B%2522request%255Fid%2522%253A%2522169649470916800226556146%2522%252C%2522scm%2522%253A%252220140713.130102334..%2522%257D&request_id=169649470916800226556146&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~all~top_positive~default-1-115444815-null-null.142^v94^control&utm_term=vscode%E5%BC%80%E5%8F%91%E5%BE%AE%E4%BF%A1%E5%B0%8F%E7%A8%8B%E5%BA%8F&spm=1018.2226.3001.4187
```

​		微信开发者工具导入 `/dist/dev/mp-weixin` 目录

### 工程结构解析

```
├── .husky                     # Git Hooks
├── .vscode                    # VS Code 插件 + 设置
├── dist                       # 打包文件夹（可删除重新打包）
├── src                        # 源代码
│   ├── components             # 全局组件
│   ├── composables            # 组合式函数
│   ├── pages                  # 主包页面
│       ├── index               # 首页
|           |—— components         # 自定义组件
|           |—— index              # 主页
|           └── productReferences  # 商品详情页
│       ├── category            # 分类页
│       ├── cart                # 购物车
│       ├── my                  # 我的
|           |—— my                 # 我的主页
|           └── myRelease          # 我的发布页面
│       |── login               # 登录页
|           |—— login              # 用户注册/登录界面
|           └── productLogin       # 商品发布页
|       |—— release             # 准备发布页
│   ├── services               # 所有请求
|       |—— index                # 主页请求处理
|       |—— login                # 用户请求处理
|       └── product              # 商品请求处理
│   ├── static                 # 存放应用引用的本地静态资源的目录
│       ├── background          # 普通背景图
|       |—— category            # 分类页图片
│       └── tabs                # tabBar 图片
│   ├── stores                 # 全局 pinia store
│       ├── modules             # 模块
|           |—— cartProductIndex  # 购物车索引
|           |—— category          # 分类页标签索引
|           |—— member            # 用户信息
|           |—— myRelease         # 我的发布信息
|           |—— productInfo       # 主页商品信息
|           └── productItemIndex  # 主页商品索引
│       └── index.ts            # store 入口
│   ├── styles                 # 全局样式
│       └── fonts.scss          # 字体图标
│   ├── types                  # 类型声明文件
|       |—— loginType           # 注册/登录请求类型
|       |—— productType         # 商品信息请求类型
│       └── component.d.ts      # 全局组件类型声明
│   ├── utils                  # 自定义方法
|       |—— http                 # http请求处理方法
|       |—— imageHandle          # 图片处理方法
|       └── stringHandle         # 字符串处理方法
│   ├── App.vue                # 入口页面
│   ├── main.ts                # Vue初始化入口文件
│   ├── pages.json             # 配置页面路由等页面类信息
│   ├── manifest.json          # 配置appid等打包信息
│   └── uni.scss               # uni-app 内置的常用样式变量
├── .editorconfig              # editorconfig 配置
├── .eslintrc.cjs              # eslint 配置
├── .prettierrc.json           # prettier 配置
├── .gitignore                 # git 忽略文件
├── index.html                 # H5 端首页
├── package.json               # package.json 依赖
├── tsconfig.json              # typescript 配置
└── vite.config.ts             # vite 配置
```
