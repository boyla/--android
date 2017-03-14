package com.myCard;

import java.util.List;


import android.util.Log;
import android.view.MotionEvent;

public class EventAction {
	/*
	 * www.javaapk.com测试
	 */
	MotionEvent event;
	private MyView view;

public static	boolean tag = true;
	
	public EventAction(MyView view, MotionEvent event) {
		this.event = event;
		this.view = view;
	}
	
	// 操作按钮事件
	public void getButton() {
		
		if (!view.hideButton) {
			float x = event.getX(), y = event.getY();
			//3分和出牌按钮
			if ((x > view.screen_width / 2 + view.cardWidth)
					&& (y > view.screen_height - view.cardHeight * 5 / 2)
					&& (x < view.screen_width / 2 + 3 * view.cardWidth)
				&& (y < view.screen_height - view.cardHeight * 11 / 6)) {
	//			Log.i("mylog", "点击三分和出牌按钮！");
				
				// 抢地主
				if (view.buttonText[1].equals("3分")) {
					
					Log.i("mylog", "你点击了3分按钮！");
					
					
					//加入地主牌
					for(Card card:view.dizhuList)
					{
						card.rear=false;
					}
					view.update();
					view.setTimer(5, 1);
//					view.setTimer(1, 1);
					view.playerList[1].addAll(view.dizhuList);
					view.dizhuList.clear();
					//设定牌的顺序
					Common.setOrder(view.playerList[1]);
					//设定顺序后重新设定位置
					Common.rePosition(view, view.playerList[1], 1);
					view.dizhuFlag=1;//地主是我;
					Common.dizhuFlag=view.dizhuFlag;
					view.update();
					view.turn=1;
				

				}
				// 出牌
				if (view.buttonText[1].equals("出牌")) {
				
				Log.i("mylog", "你点击了出牌按钮！");
				
					// 选出最好的出牌(跟牌和主动出牌)
					List<Card> oppo = null;
					if (view.outList[0].size() <= 0
							&& view.outList[2].size() <= 0) {
						oppo = null;
					} else {
						oppo = (view.outList[0].size() > 0) ? view.outList[0]
								: view.outList[2];
					}
					List<Card> mybest = Common.getMyBestCards(
							view.playerList[1], oppo);
					// Common.getBestAI(view.playerList[1],null);
					if (mybest == null)
						return;
					synchronized (view) {
						// 加入outlist
						view.outList[1].clear();
						view.outList[1].addAll(mybest);
						// 退出playerlist
						view.playerList[1].removeAll(mybest);
					}
					Common.rePosition(view, view.playerList[1], 1);
					view.flag[1] = 1;
					view.message[1] = "";
					view.nextTurn();
					view.update();
				}
				view.hideButton = !view.hideButton;
					}
			// 不抢和提示
			if (x > view.screen_width / 2 + 4 * view.cardWidth
					&& y > view.screen_height - view.cardHeight * 5 / 2
					&& x < view.screen_width / 2 + 6 * view.cardWidth
					&& y < view.screen_height - view.cardHeight * 11 / 6) {
//				Log.i("mylog", "点击了不抢和提示！");
//				// 不抢
				if (view.buttonText[2].equals("不抢")) {
					
					Log.i("mylog", "你点击不抢按钮！");
					view.dizhuFlag = Common.getBestDizhuFlag();
					// view.dizhuFlag=0;
					Common.dizhuFlag = view.dizhuFlag;
					for (Card card : view.dizhuList) {
						card.rear = false;// 翻开
					}
					view.update();
					view.Sleep(3000);
					for (Card card : view.dizhuList) {
						card.rear = true;// 关上
					}
					view.playerList[view.dizhuFlag].addAll(view.dizhuList);
					view.dizhuList.clear();
					Common.setOrder(view.playerList[view.dizhuFlag]);
					Common.rePosition(view, view.playerList[view.dizhuFlag],
							view.dizhuFlag);
					view.update();

					view.turn = view.dizhuFlag;
					view.hideButton = true;
				}
				// 提示
				if (view.buttonText[2].equals("提示")) {
					Log.i("mylog", "你点击提示按钮！");
					Cardcsh();
					view.update();
				}
			}

			// 一分和透视
			if (x > view.screen_width / 2 - 6 * view.cardWidth
					&& y > view.screen_height - view.cardHeight * 5 / 2
					&& x < view.screen_width / 2 - 4 * view.cardWidth
					&& y < view.screen_height - view.cardHeight * 11 / 6) {
				
	//			Log.i("mylog", "点击了一分和透视！");
				
				// 如果是1分按钮
				if (view.buttonText[3].equals("1分")) {
					view.dizhuFlag = Common.getBestDizhuFlag();
					// view.dizhuFlag=0;
					
					//翻开3张地主的牌
					Common.dizhuFlag = view.dizhuFlag;
					for (Card card : view.dizhuList) {
						card.rear = false;// 翻开
					}
					view.update();
					view.Sleep(2000);
					for (Card card : view.dizhuList) {
						card.rear = true;// 关上
					}
					

					view.turn = view.dizhuFlag;
					view.hideButton = true;
				}
				// 如果是透视按钮
				
				if (view.buttonText[3].equals("透视")) {
				
					Log.i("mylog", "你点击了透视");
					
					System.out.println("原始数据为："+tag);
					if(tag){
							view.update();
							view.message[1] = "开启透视功能";
							view.Sleep(1000);
							for (Card card : view.card) {
								card.rear = false;// 翻开
								
							}
							tag  = false;
						
							System.out.println("开启后为："+tag);
							return;
					}
					
					if(!tag){						
						for (Card card : view.card) {
							card.rear = true;// 合上
							//System.out.println("view.card");
						}
						for (Card card : view.playerList[1]){
							
							card.rear = false;// 打开
						//	System.out.println("打开");
							
						}
						view.dizhuList.clear();
						view.update();
						view.message[1] = "关闭透视功能";
						view.Sleep(1000);
						tag=true;
		//				System.out.println("关闭"+tag);
					}
					
					
				
					view.flag[1] = 0;
					view.update();
					
				}

			}

			// 两分和不出
			if (x > view.screen_width / 2 - 3 * view.cardWidth
					&& y > view.screen_height - view.cardHeight * 5 / 2
					&& x < view.screen_width / 2 - view.cardWidth
					&& y < view.screen_height - view.cardHeight * 11 / 6) {
//				Log.i("mylog", "点击了两分和不出！");
//				// 两分
				if (view.buttonText[0].equals("2分")) {
					
					Log.i("mylog", "你点击了2分按钮！");
				
					view.dizhuFlag = Common.getBestDizhuFlag();
					// view.dizhuFlag=0;
					Common.dizhuFlag = view.dizhuFlag;
					for (Card card : view.dizhuList) {
						card.rear = false;// 翻开
					}
					view.update();
					view.Sleep(3000);
					for (Card card : view.dizhuList) {
						card.rear = true;// 关上
					}
					view.playerList[view.dizhuFlag].addAll(view.dizhuList);
					view.dizhuList.clear();
					Common.setOrder(view.playerList[view.dizhuFlag]);
					Common.rePosition(view, view.playerList[view.dizhuFlag],
							view.dizhuFlag);
					view.update();

					view.turn = view.dizhuFlag;
					view.hideButton = true;
				}
//		     	// 不出
				if (view.buttonText[0].equals("不要")) {
					
					Log.i("mylog", "你点击了不要按钮！");
				
					
					if(view.outList[0].size()==0&&view.outList[2].size()==0){
						Log.i("mylog", "不能不不要");
						return;
					}
					Log.i("mylog", "不要");
					view.message[1]="不要";
					view.hideButton=true;
					view.nextTurn();
					view.flag[1]=0;
					view.update();
				}
					

			}

		}
	}

	// 取出点击的是哪张牌
	public Card getCard() {
		Card card = null;
		float x = event.getX();// 触摸x坐标
		float y = event.getY();// 触摸y坐标
		float xoffset = view.cardWidth * 4 / 5f;
		float yoffset = view.cardHeight;
		// y <  屏幕高度 - 4* 牌的高度
		if (y < view.screen_height - 4 * view.cardHeight / 3)
			return null;
		for (Card card2 : view.playerList[1]) {
			if (card2.clicked) {
				// 查询符合范围的
				if ((x - card2.x > 0)
						&& (y - card2.y > 0)
						&& (((x - card2.x < xoffset) && (y - card2.y < yoffset)) || ((x
								- card2.x < card2.width) && (y - card2.y < card2.height / 3)))) {
					return card2;
				}
			} else {
				// 查询符合范围的
				if ((x - card2.x > 0) && (x - card2.x < xoffset)
						&& (y - card2.y > 0) && (y - card2.y < yoffset)) {
					return card2;
				}
			}
		}

		return card;
	}
	
	// 初始化点击的牌
		public Card Cardcsh() {
			
	//		System.out.println("运行Cardcsh");
			
			// 初始化所有被点击过得牌，
			for (Card card : view.playerList[1]){
				
				card.y = view.screen_height -  view.cardHeight;
				
				//	System.out.println("设置全部没有点击");
				card.clicked=false;
				
			}

			
	
			
			
			//List<Card> from, List<Card> opp, List<Card> to
			//oppo是对手出的牌,from是自己已有的牌,to是药走出的牌
			
			Common common = new Common();
			common.bulidts();
			view.update();
			return null;
		}
	
}
