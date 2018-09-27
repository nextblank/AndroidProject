##Swiper的使用
###.js文件
```
var imgUrls = [=
	{ image: "http://www.padmag.cn/wp-content/uploads/Unforgiven.jpg" },
  { image: "http://www.padmag.cn/wp-content/uploads/Desire.jpg" },
  { image: "http://www.padmag.cn/wp-content/uploads/No_Goodbye.jpg" },
  { image: "http://www.padmag.cn/wp-content/uploads/Look_Away.jpg" }
]
Page({
  data: {
imgUrls: imgUrls,
autoplay: true,//是否自动切换  
indicatorDots: true,//是否显示圆点  
interval: 5000,//自动切换间隔  
duration: 500, //滑动动画时长  
indicatorColor: "blue",//滑动圆点颜色  
indicatorActiveColor: "white", //当前圆点颜色  
current: 2, //当前所在页面的 index  
circular: true  //是否采用衔接滑动  
//其中只可放置<swiper-item/>组件，否则会导致未定义的行为。  
  }, imageLoad: function () {
//bindload 图片加载的时候自动设定宽度  
this.setData({
  imageWidth: wx.getSystemInfoSync().windowWidth
})
  }, swiperChange: function () {
console.log("current 改变时会触发 change 事件")
  }
})  
```
###.wxml文件
```<!--index.wxml-->
<swiper indicator-dots="{{indicatorDots}}" current="{{current}}" circular="{{circular}}" indicator-active-color="{{indicatorActiveColor}}" indicator-color="{{indicatorColor}}" autoplay="{{autoplay}}" interval="{{interval}}" duration="{{duration}}" bindchange="swiperChange">
  <block wx:for="{{imgUrls}}" wx:key="image">
<swiper-item>
  <image src="{{item.image}}" model="aspectFit" style="width: {{imageWidth}}px;" bindload="imageLoad" />
</swiper-item>
  </block>
</swiper>
```