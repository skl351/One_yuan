package com.yiyuangou.android.one_yuan.util;

/**
 * Created by android on 2016/3/22.
 */
public  class all_url {
//            public static String url_zong="http://www.hzxxyx.cn:8080/jc/mobile/";//外网
            public static String url_zong="http://119.63.44.123:8080/jc/mobile/";//外网
//    public static String url_zong="http://www.isdjs.cn/jc/mobile/";//外网--sh
    //        public static String url_zong="http://192.168.0.141:8080/jc/mobile/";//本地
//        public static String url_zong="http://139.196.17.42:8080/jc/mobile/";//外网
//    public static String url_zong="http://192.168.0.188:8080/jc/mobile/";
//    public static String url_zong="http://192.168.0.222:8080/jc/mobile/";
    public static String url_zong_about_lol=url_zong+"lol";
    public static String url_zong_about_lolyy=url_zong+"lolyy";
    public static String url_register_check=url_zong+"checkMobile";//注册核对手机号
    public static String url_register_sms=url_zong+"smsCode";//注册短息
    public static String url_register_sms_check=url_zong+"validateCode";//注册短息验证
    public static String url_xiugainame=url_zong+"updateUserName";//用户登陆-----
    public static String url_login=url_zong+"login";//昵称修改
    public static String url_regist=url_zong+"regist";//用户注册
    public static String url_modify=url_zong+"resetPd";//用户密码修改
    public static String url_forgot=url_zong+"smsCodePwd";//忘记密码
    public static String url_forgot_modify=url_zong+"forgetPwd";//忘记密码-修改
    public static String url_user_info=url_zong+"userInfo";//用户信息
    public static String url_calcel_kaicai=url_zong_about_lol+"/stopBanker";//
    public static String url_calcel_kaicai2=url_zong_about_lolyy+"/stopBanker";//
    public static String url_lol_xiahzuxianzhu=url_zong_about_lol+"/betNum";//lolxiazhuxianshu
    public static String url_lol_xiahzuxianzhu2=url_zong_about_lolyy+"/betNum";//lolxiazhuxianshu
    public static String url_shenqingliebiao=url_zong_about_lol+"/applyBanker";//申请开猜
    public static String url_shenqingliebiao2=url_zong_about_lolyy+"/applyBanker";//申请开猜
    public static String url_shenqingliebiao_before=url_zong_about_lol+"/banker";//申请开猜之前
    public static String url_shenqingliebiao_before2=url_zong_about_lolyy+"/banker";//申请开猜之前
    public static String url_kaicailliebiao=url_zong_about_lol+"/bankerList";//开猜列表
    public static String url_kaicailliebiao2=url_zong_about_lolyy+"/bankerList";//开猜列表
    public static String url_jincai=url_zong+"guess/jcContent";//大接口
    public static String url_lol=url_zong_about_lol+"/jcContent";//lol首页
    public static String url_lol2=url_zong_about_lolyy+"/jcContent";//lol首页
    public static String url_one_buy="http://192.168.0.222/login/indexAuto.html";//一元购_网站
    public static String url_order=url_zong+"pay/placeOrder";//下单
    public static String url_order2=url_zong+"pk10pay/placeOrder";//下单2
    public static String url_order2_lol=url_zong_about_lol+"pay/placeOrder";//下单2
    public static String url_order2_lol2=url_zong+"lolpayyy/placeOrder";//下单2
    public static String url_open_shuaxin=url_zong+"guess/openCode";//刷新
    public static String url_open_more=url_zong+"guess/moreOpenCode";//得到更多
    public static String url_open_more_lol=url_zong_about_lol+"moreOpenCode";//得到更多lol
    public static String url_open_more_lol2=url_zong_about_lolyy+"moreOpenCode";//得到更多lol
    public static String url_before_order=url_zong+"pay/beforePlaceOrder";//下单之前
    public static String url_before_order_lol=url_zong+"lolpay/beforePlaceOrder";//下单之前lol
    public static String url_before_order_lol2=url_zong+"lolpayyy/beforePlaceOrder";//下单之前lol
    public static String url_before_order2=url_zong+"pk10pay/beforePlaceOrder";//下单之前
    public static String url_pay=url_zong+"pay/placePayOrder";//支付
    public static String url_paylol=url_zong_about_lol+"pay/placePayOrder";//支付-lolk
    public static String url_paylol2=url_zong+"lolpayyy/placePayOrder";//支付-lolk
    public static String url_pay2=url_zong+"pk10pay/placePayOrder";//支付2
    public static String url_addAddress=url_zong+"addAddress";//添加收货地址
    public static String url_getAddress=url_zong+"getAddress";//得到收货地址
    public static String url_updateAddress=url_zong+"updateAddress";//修改收货地址
    public static String url_setDefault =url_zong+"setDefault";//默认收货地址
    public static String url_delAddress =url_zong+"delAddress";//删除收货地址
    public static String url_face =url_zong+"img/faceImg";//上传头像
    public static String url_jincaidingdan=url_zong+"log/orders";//用户竞猜订单    userId-支付用户id
    public static String url_jincaidingdan_hero=url_zong+"log/ordersLol";//用户英雄订单    userId-支付用户id
    public static String url_jincaidingdan_hero2=url_zong+"log/ordersLolyy";//用户英雄订单    userId-支付用户id
    public static String url_jincaidingdan_pk=url_zong+"log/ordersPK10";//用户竞猜订单pk
    public static String url_kaijiang_shuoming=url_zong+"index/getConfig?PZID=10012";//开奖说明
    public static String url_pk_config=url_zong+"index/getConfig?PZID=10039";//开奖说明pk
    public static String url_jigncai_config=url_zong+"index/getConfig?PZID=10038";//开奖说明竞猜
    public static String url_fenxiang_xiangguang=url_zong+"index/getConfig?PZID=10035";//微信分享相关
    public static String url_yiyuan=url_zong+"index/getConfig?PZID=10032";//yiyuangou
    public static String url_zhuanpan_info=url_zong+"index/getConfig?PZID=10031";//转盘规则
    public static String url_jingcaiguizhe=url_zong+"index/getConfig?PZID=10025";//竞猜规则
    public static String url_hero_guizhe=url_zong+"index/getConfig?PZID=10040";//竞猜英雄规则
    public static String url_hero2_guizhe=url_zong+"index/getConfig?PZID=10047";//竞猜英雄2规则
    public static String url_hero_time=url_zong+"index/getConfig?PZID=10042";//时间
    public static String url_pk_guizhe=url_zong+"index/getConfig?PZID=10037";//pk规则
    public static String url_ruheshouyi=url_zong+"index/getConfig?PZID=10036";//如何收益
    public static String url_xiazhubangshuoming=url_zong+"index/getConfig?PZID=10029";//xiazhu规则
    public static String url_chongzhi=url_zong+"pay/placeCzOrder";//充值 传入参数： ZFYHID-支付用户id  ZFJE-支付金额   ZFFS( 1-京东 )  ZFDDID(支付订单主键id)  ZFLX（   1-金豆充值   2-余额充值)
    public static String url_jingdongwangying=url_zong+"pay/sendJD";//网银 传入参数 ：  ZFDDID  支付订单id   DDBH  订单编号
    public static String url_suggest=url_zong+"feedBack";//意见反馈sdsdsds
    public static String url_chongzhijilu=url_zong+"log/payLogs";//充值记录
    public static String url_send_index=url_zong+"donate/index";//赠送首页
    public static String url_send=url_zong+"donate/send";//赠送  phone 手机号
    public static String url_send_ok=url_zong+"donate/sendConfirm";//确定赠送 传入参数：传入参数 SPID 赠送商品的id	   YHID 登陆用户id    phone 对方手机号
    public static String url_sended=url_zong+"donate/hasDonate";//ZSYHID  当前用户id
    public static String url_jd_dh=url_zong+"donate/exchange";//donatelog_id  赠送列表的uuid
    public static String url_qiandao=url_zong+"task/signIn";//签到
    public static String url_task_list=url_zong+"task/index";//任务列表
    public static String url_task_relief=url_zong+"task/relief";//救济
    public static String url_task_yaoq=url_zong+"task/invite";//邀请
    public static String url_task_give_jd=url_zong+"task/play";//玩几次送金豆
    public static String url_caifubang=url_zong+"index/bankingListCf";//财富榜
    public static String url_darenbang=url_zong+"index/bankingListYl";//达人榜
    public static String url_xiazhubang=url_zong+"index/bankingListXZ";//下注榜
    public static String url_index=url_zong+"index/index";//首页
    public static String url_gift=url_zong+"index/giftList";//礼物列表
    public static String url_orderinfo=url_zong+"log/orderInfos";//jingcaidingdanxiangq
    public static String url_orderinfo_lol=url_zong+"log/orderInfosLol";//info——heroxiangq
    public static String url_orderinfo_lolyy=url_zong+"log/orderInfosLolyy";//info——heroxiangq
    public static String url_orderinfo_pk=url_zong+"log/orderInfosPK10";//pk_xiangq
    public static String url_zhongjian_mingdan=url_zong+"index/winList";//传入参数中奖号码
    public static String getUrl_gift_duihuan_dingdan=url_zong+"log/giftExchangeLog";//礼物兑换订单 传入参数：userId
    public static String url_gift_duihuan=url_zong+"index/giftExchange";//礼物duihuan //传入参数：userId  giftId（礼品表主键id）  id（收货地址主键id）
    public static String url_xieyi=url_zong+"goPro";//协议
    public static String url_getversion=url_zong+"index/getAppVersion";//得到最新版本
    public static String url_erweima=url_zong+"tuiguang/html/index.jsp";//得到最新版本 mobile
    public static String url_zengsong_ok_ma=url_zong+"donate/beforeSend";//赠送第一步可以的有一个判断
    public static String url_zhuanpaninfo=url_zong+"award/index";//转盘信息 roomId=
    public static String url_kaijiang=url_zong+"award/start";//开奖 roomid=&userId=
    public static String url_room=url_zong+"award/room";//房间
    public static String url_huiyuan=url_zong+"myUsers";//我的会员
    public static String url_tuikuang=url_zong+"tuiguang/html/index.jsp";//推广
    public static String url_jiangli=url_zong+"log/rewardLogs";//奖励记录
    public static String url_tuwen=url_zong+"index/giftImgs";//房间 http://192.168.0.222:8080/jc/mobile/index/giftImgs?giftId=fb4fa3ff9da74148b0a8c5d4889bd991
    public static String url_get_thing=url_zong+"award/receive";//领取实物 http://192.168.0.222:8080/jc/mobile/award/receive
    public static String url_zhuanpan_dingdan=url_zong+"log/awardLogs";//房间 http://192.168.0.222:8080/jc/mobile/log/awardLogs?userId=1001639605&PAGE=1&NUM=10
    public static String url_pkindex=url_zong+"pk/jcContent";//pk拾首页
    public static String url_opennumber=url_zong+"pk/openCode";//pk开奖号码
    public static String url_more=url_zong+"pk/moreOpenCode";//pk拾更多开奖记录
    public static String url_talk_list=url_zong+"feedBackList";//talk_rote
    public static String url_gonggao=url_zong+"getNotice";//gonggao
    public static String url_kaicai_ingo=url_zong+"log/ordersBankerLol";//开猜订单
    public static String url_kaicai_ingo2=url_zong+"log/ordersBankerLolyy";//开猜订单
    public static String APP_ID="wxa9f35a9345c430f5";
}
