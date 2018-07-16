package com.brian.item;

import com.brian.item.IEnum;

public class LotteryInfoEnum {
	
	public enum Lottery_Info implements IEnum{
		LOTTETY_MMC("合一秒秒彩","50"),
		LOTTETY_SSCCS("合一分分彩","15"),
		LOTTETY_SSCBF("合一两分彩","16"),
		LOTTETY_SSCCQ("重庆时时彩","1"),
		LOTTETY_SSCXJ("新疆时时彩","5"),
		LOTTETY_SSCTJ("天津时时彩","14"),
		LOTTETY_DT115("合一十一选五","18"),
		LOTTETY_SD115("山东十一选五","2"),
		LOTTETY_GD115("广东十一选五","9"),
		LOTTETY_JS115("江苏十一选五","4"),
		LOTTETY_JX115("江西十一选五","8"),
		LOTTETY_DT3D("合一3D","17"),
		LOTTETY_P3P5("体彩P3P5","12"),
		LOTTETY_FC3D("福彩3D","11"),
		LOTTETY_PK10("分分PK10","83"),
		LOTTETY_GCPK10("北京PK10","85"),
		LOTTETY_HG1_5("菲律宾1.5分彩","3"),
		LOTTETY_QQ("腾讯分分彩","6");
		
		
		
		private String outername;
		private String value;
		
		private Lottery_Info(String outername, String value){
			this.value = value;
			this.outername = outername;
		}

		@Override
		public String getOutername() {
			return outername;
		}

		@Override
		public String getValue() {
			return value;
		}
		
		public static String getOutername(String value){
			String outername = "";
			for(Lottery_Info obj : LotteryInfoEnum.Lottery_Info.values()){
				if(obj.getValue().equals(value)){
					outername = obj.getOutername();
				}
			}
			return outername;
		}
		
	}
	
	public enum Lottery_ID_URL implements IEnum{
		LOTTETY_MMC("/mmc.shtml","50"),
		LOTTETY_SSCCQ("/ssccq.shtml","1"),
		LOTTETY_SSCBF("/sscbf.shtml","16"),
		LOTTETY_SSCCS("/ssccs.shtml","15"),
		LOTTETY_SSCXJ("/sscxj.shtml","5"),
		LOTTETY_SSCTJ("/ssctj.shtml","14"),
		LOTTETY_DT115("/115cs.shtml","18"),
		LOTTETY_SD115("/115sd.shtml","2"),
		LOTTETY_GD115("/115gd.shtml","9"),
		LOTTETY_JS115("/jiangsu.shtml","4"),
		LOTTETY_JX115("/115jx.shtml","8"),
		LOTTETY_DT3D("/cs3d.shtml","17"),
		LOTTETY_P3P5("/pl35.shtml","12"),
		LOTTETY_FC3D("/fc3d.shtml","11"),
		LOTTETY_PK10("/pk10.shtml","83"),
		LOTTETY_GCPK10("/pk105f.shtml","85"),
		LOTTETY_HG1_5("/hg1_5.shtml","3"),
		LOTTETY_QQ("/qq.shtml","6");
		
		private String outername;
		private String value;
		
		private Lottery_ID_URL(String outername, String value){
			this.value = value;
			this.outername = outername;
		}

		@Override
		public String getOutername() {
			return outername;
		}

		@Override
		public String getValue() {
			return value;
		}
		
		public static String getOutername(String value){
			String outername = "";
			for(Lottery_ID_URL obj : LotteryInfoEnum.Lottery_ID_URL.values()){
				if(obj.getValue().equals(value)){
					outername = obj.getOutername();
				}
			}
			return outername;
		}
		
	}

}
