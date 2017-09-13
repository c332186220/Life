package com.cxl.life.util;

import com.cxl.life.bean.KingGlory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cxl on 2017/6/22.
 * 添加测试数据
 */

public class TestUtil {

    private static String[] king1 = new String[]{"干将莫邪", "东皇太一", "大乔", "黄忠", "诸葛亮", "哪吒", "太乙真人", "杨戬", "成吉思汗", "橘右京", "马可波罗", "雅典娜", "夏侯惇", "蔡文姬", "关羽", "虞姬", "安琪拉", "项羽",
            "后羿", "刘禅", "王昭君", "赵云", "花木兰", "狄仁杰", "韩信", "墨子", "鲁班七号", "宫本武藏", "牛魔", "貂蝉", "典韦", "小乔", "甄姬", "妲己", "孙膑", "武则天", "高渐离", "吕布", "孙悟空", "孙尚香", "达摩", "程咬金", "扁鹊",
            "亚瑟", "阿轲", "姜子牙", "周瑜", "老夫子", "嬴政", "曹操", "庄周", "张良", "钟无艳", "芈月", "露娜", "廉颇", "白起", "张飞", "刘备", "兰陵王", "娜可露露", "李白", "钟馗", "李元芳", "刘邦", "不知火舞"};
    private static int[] king2 = new int[]{182, 187, 191, 192, 190, 180, 186, 178, 177, 163, 132, 183, 126, 184, 140, 174, 142, 135, 169, 114, 152, 107, 154, 133, 150, 108, 112, 130, 168, 141, 129, 106, 127, 109, 118, 136, 115, 123, 167, 111, 134,
            144, 119, 166, 116, 148, 124, 139, 110, 128, 113, 156, 117, 121, 146, 105, 120, 171, 170, 153, 162, 131, 175, 173, 149, 157};
    public static HashMap<String, String> layoutTitle;

    public static List<KingGlory> getKingGlory() {
        List<KingGlory> list = new ArrayList<>();
        KingGlory king;
        Map<String, String> map = addDialogue();
        for (int i = 0; i < king1.length; i++) {
            king = new KingGlory(king1[i], "http://game.gtimg.cn/images/yxzj/img201606/heroimg/" + king2[i] + "/" + king2[i] + ".jpg", "http://pvp.qq.com/web201605/herodetail/" + king2[i] + ".shtml");
            king.setDialogue(map.get(king.getName()));
            list.add(king);
        }
        return list;
    }

    private static Map<String, String> addDialogue() {
        Map<String, String> map = new HashMap<>();
        map.put("干将莫邪", "一分为二的生命，独一无二的魂灵。");
        map.put("东皇太一", "日蚀亲临，如我之神迹！");
        map.put("大乔", "守望着天空，大海，和你的回忆。");
        map.put("黄忠", "是时候来发炸裂的开场了！");
        map.put("诸葛亮", "天下如棋，一步三算！");
        map.put("哪吒", "我可是突破常理的存在。");
        map.put("太乙真人", "皇家认证，特级炼金术师是也！炉子，炉子也有资格证书~");
        map.put("杨戬", "执行人间的意志");
        map.put("成吉思汗", "驰骋草原，碎裂星辰");
        map.put("橘右京", "申し訳ありません");
        map.put("马可波罗", "世界那么大，我想来看看");
        map.put("雅典娜", "委身于时光，制裁以死亡！");
        map.put("夏侯惇", "独眼，是男人的浪漫！");
        map.put("蔡文姬", "来~一起玩游戏吧！");
        map.put("关羽", "胜利与信念，都交托阁下！");
        map.put("虞姬", "明媚如风，轻盈似箭！");
        map.put("安琪拉", "知识，就是力量！");
        map.put("项羽", "我命由我");
        map.put("后羿", "苏醒了，猎杀时刻！");
        map.put("刘禅", "龙城是我家，老爹最伟大！");
        map.put("王昭君", "凛冬已至。");
        map.put("赵云", "赵子龙，参见！");
        map.put("花木兰", "姐可是传说！");
        map.put("狄仁杰", "真相只有一个！");
        map.put("韩信", "不做无法实现的梦！");
        map.put("墨子", "变形，出发！");
        map.put("鲁班七号", "鲁班七号，智商二百五，鲁班，金刚鲁班");
        map.put("宫本武藏", "天下无双。");
        map.put("牛魔", "牛气冲天，纯爷们！");
        map.put("貂蝉", "不要爱上妾身哟！");
        map.put("典韦", "身体里沉睡的野兽，觉醒了！");
        map.put("小乔", "恋爱和战斗都要勇往直前。");
        map.put("甄姬", "真的，会有人选择阿福吗");
        map.put("妲己", "请尽情吩咐妲己吧，主人。");
        map.put("孙膑", "人家这么可爱，当然是男孩子了。");
        map.put("武则天", "奉我为主。");
        map.put("高渐离", "该我上场表演啦");
        map.put("吕布", "我是这个世界的梦魇~");
        map.put("孙悟空", "俺老孙来也！");
        map.put("孙尚香", "大小姐驾到！统统闪开");
        map.put("达摩", "肩挑凡世，拳握初心。");
        map.put("程咬金", "一个字：干！");
        map.put("扁鹊", "生存还是死亡，这是个问题。想要操纵它？愚不可及！");
        map.put("亚瑟", "因剑而生，因剑而亡！");
        map.put("阿轲", "不是你记忆中的荆轲，但致命的程度，没两样");
        map.put("姜子牙", "愿者上钩，这是多么痛彻的领悟啊！");
        map.put("周瑜", "可以怀疑我的人格，不许侮辱我的造型");
        map.put("老夫子", "天不生夫子，万古如长夜。");
        map.put("嬴政", "天上天下，唯朕独尊！");
        map.put("曹操", "宁教我负天下人！");
        map.put("庄周", "蝴蝶是我，我就是蝴蝶");
        map.put("张良", "我思故我在");
        map.put("钟无艳", "给你的麻烦开个价吧");
        map.put("芈月", "征服了男人，也就征服了世界");
        map.put("露娜", "替月行道");
        map.put("廉颇", "我可是重量级的");
        map.put("白起", "身在黑暗，心向光明");
        map.put("张飞", "心有猛虎！");
        map.put("刘备", "深刻而不深沉，平淡而不平庸");
        map.put("兰陵王", "刀锋所划之地，便是疆土。");
        map.put("娜可露露", "ありがとう、ママハハ");
        map.put("李白", "大河之剑天上来！");
        map.put("钟馗", "传说中的长安城管，登场！");
        map.put("李元芳", "秘密的密，探案的探！");
        map.put("刘邦", "不客观的说，我是个好人！");
        map.put("不知火舞", "かちょうせん");
        return map;
    }

    public static List<String> getLayoutTitle() {
        List<String> titleList = new ArrayList<>();
        layoutTitle = new HashMap<>();
        layoutTitle.put("excel解析", "6");
        layoutTitle.put("json解析", "5");
        layoutTitle.put("富文本", "4");
        layoutTitle.put("中文名跨行", "3");
        layoutTitle.put("自定义键盘", "2");

//        for (String title : layoutTitle.keySet()) {
//            titleList.add(title);
//        }
        titleList.add("浮动布局");
        for (Map.Entry<String, String> entry : layoutTitle.entrySet()) {
            titleList.add(entry.getKey());
        }
        layoutTitle.put("浮动布局", "1");
        return titleList;
    }

}
