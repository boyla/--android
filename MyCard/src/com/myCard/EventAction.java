package com.myCard;

import java.util.List;


import android.util.Log;
import android.view.MotionEvent;

public class EventAction {
	/*
	 * www.javaapk.com����
	 */
	MotionEvent event;
	private MyView view;

public static	boolean tag = true;
	
	public EventAction(MyView view, MotionEvent event) {
		this.event = event;
		this.view = view;
	}
	
	// ������ť�¼�
	public void getButton() {
		
		if (!view.hideButton) {
			float x = event.getX(), y = event.getY();
			//3�ֺͳ��ư�ť
			if ((x > view.screen_width / 2 + view.cardWidth)
					&& (y > view.screen_height - view.cardHeight * 5 / 2)
					&& (x < view.screen_width / 2 + 3 * view.cardWidth)
				&& (y < view.screen_height - view.cardHeight * 11 / 6)) {
	//			Log.i("mylog", "������ֺͳ��ư�ť��");
				
				// ������
				if (view.buttonText[1].equals("3��")) {
					
					Log.i("mylog", "������3�ְ�ť��");
					
					
					//���������
					for(Card card:view.dizhuList)
					{
						card.rear=false;
					}
					view.update();
					view.setTimer(5, 1);
//					view.setTimer(1, 1);
					view.playerList[1].addAll(view.dizhuList);
					view.dizhuList.clear();
					//�趨�Ƶ�˳��
					Common.setOrder(view.playerList[1]);
					//�趨˳��������趨λ��
					Common.rePosition(view, view.playerList[1], 1);
					view.dizhuFlag=1;//��������;
					Common.dizhuFlag=view.dizhuFlag;
					view.update();
					view.turn=1;
				

				}
				// ����
				if (view.buttonText[1].equals("����")) {
				
				Log.i("mylog", "�����˳��ư�ť��");
				
					// ѡ����õĳ���(���ƺ���������)
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
						// ����outlist
						view.outList[1].clear();
						view.outList[1].addAll(mybest);
						// �˳�playerlist
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
			// ��������ʾ
			if (x > view.screen_width / 2 + 4 * view.cardWidth
					&& y > view.screen_height - view.cardHeight * 5 / 2
					&& x < view.screen_width / 2 + 6 * view.cardWidth
					&& y < view.screen_height - view.cardHeight * 11 / 6) {
//				Log.i("mylog", "����˲�������ʾ��");
//				// ����
				if (view.buttonText[2].equals("����")) {
					
					Log.i("mylog", "����������ť��");
					view.dizhuFlag = Common.getBestDizhuFlag();
					// view.dizhuFlag=0;
					Common.dizhuFlag = view.dizhuFlag;
					for (Card card : view.dizhuList) {
						card.rear = false;// ����
					}
					view.update();
					view.Sleep(3000);
					for (Card card : view.dizhuList) {
						card.rear = true;// ����
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
				// ��ʾ
				if (view.buttonText[2].equals("��ʾ")) {
					Log.i("mylog", "������ʾ��ť��");
					Cardcsh();
					view.update();
				}
			}

			// һ�ֺ�͸��
			if (x > view.screen_width / 2 - 6 * view.cardWidth
					&& y > view.screen_height - view.cardHeight * 5 / 2
					&& x < view.screen_width / 2 - 4 * view.cardWidth
					&& y < view.screen_height - view.cardHeight * 11 / 6) {
				
	//			Log.i("mylog", "�����һ�ֺ�͸�ӣ�");
				
				// �����1�ְ�ť
				if (view.buttonText[3].equals("1��")) {
					view.dizhuFlag = Common.getBestDizhuFlag();
					// view.dizhuFlag=0;
					
					//����3�ŵ�������
					Common.dizhuFlag = view.dizhuFlag;
					for (Card card : view.dizhuList) {
						card.rear = false;// ����
					}
					view.update();
					view.Sleep(2000);
					for (Card card : view.dizhuList) {
						card.rear = true;// ����
					}
					

					view.turn = view.dizhuFlag;
					view.hideButton = true;
				}
				// �����͸�Ӱ�ť
				
				if (view.buttonText[3].equals("͸��")) {
				
					Log.i("mylog", "������͸��");
					
					System.out.println("ԭʼ����Ϊ��"+tag);
					if(tag){
							view.update();
							view.message[1] = "����͸�ӹ���";
							view.Sleep(1000);
							for (Card card : view.card) {
								card.rear = false;// ����
								
							}
							tag  = false;
						
							System.out.println("������Ϊ��"+tag);
							return;
					}
					
					if(!tag){						
						for (Card card : view.card) {
							card.rear = true;// ����
							//System.out.println("view.card");
						}
						for (Card card : view.playerList[1]){
							
							card.rear = false;// ��
						//	System.out.println("��");
							
						}
						view.dizhuList.clear();
						view.update();
						view.message[1] = "�ر�͸�ӹ���";
						view.Sleep(1000);
						tag=true;
		//				System.out.println("�ر�"+tag);
					}
					
					
				
					view.flag[1] = 0;
					view.update();
					
				}

			}

			// ���ֺͲ���
			if (x > view.screen_width / 2 - 3 * view.cardWidth
					&& y > view.screen_height - view.cardHeight * 5 / 2
					&& x < view.screen_width / 2 - view.cardWidth
					&& y < view.screen_height - view.cardHeight * 11 / 6) {
//				Log.i("mylog", "��������ֺͲ�����");
//				// ����
				if (view.buttonText[0].equals("2��")) {
					
					Log.i("mylog", "������2�ְ�ť��");
				
					view.dizhuFlag = Common.getBestDizhuFlag();
					// view.dizhuFlag=0;
					Common.dizhuFlag = view.dizhuFlag;
					for (Card card : view.dizhuList) {
						card.rear = false;// ����
					}
					view.update();
					view.Sleep(3000);
					for (Card card : view.dizhuList) {
						card.rear = true;// ����
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
//		     	// ����
				if (view.buttonText[0].equals("��Ҫ")) {
					
					Log.i("mylog", "�����˲�Ҫ��ť��");
				
					
					if(view.outList[0].size()==0&&view.outList[2].size()==0){
						Log.i("mylog", "���ܲ���Ҫ");
						return;
					}
					Log.i("mylog", "��Ҫ");
					view.message[1]="��Ҫ";
					view.hideButton=true;
					view.nextTurn();
					view.flag[1]=0;
					view.update();
				}
					

			}

		}
	}

	// ȡ���������������
	public Card getCard() {
		Card card = null;
		float x = event.getX();// ����x����
		float y = event.getY();// ����y����
		float xoffset = view.cardWidth * 4 / 5f;
		float yoffset = view.cardHeight;
		// y <  ��Ļ�߶� - 4* �Ƶĸ߶�
		if (y < view.screen_height - 4 * view.cardHeight / 3)
			return null;
		for (Card card2 : view.playerList[1]) {
			if (card2.clicked) {
				// ��ѯ���Ϸ�Χ��
				if ((x - card2.x > 0)
						&& (y - card2.y > 0)
						&& (((x - card2.x < xoffset) && (y - card2.y < yoffset)) || ((x
								- card2.x < card2.width) && (y - card2.y < card2.height / 3)))) {
					return card2;
				}
			} else {
				// ��ѯ���Ϸ�Χ��
				if ((x - card2.x > 0) && (x - card2.x < xoffset)
						&& (y - card2.y > 0) && (y - card2.y < yoffset)) {
					return card2;
				}
			}
		}

		return card;
	}
	
	// ��ʼ���������
		public Card Cardcsh() {
			
	//		System.out.println("����Cardcsh");
			
			// ��ʼ�����б���������ƣ�
			for (Card card : view.playerList[1]){
				
				card.y = view.screen_height -  view.cardHeight;
				
				//	System.out.println("����ȫ��û�е��");
				card.clicked=false;
				
			}

			
	
			
			
			//List<Card> from, List<Card> opp, List<Card> to
			//oppo�Ƕ��ֳ�����,from���Լ����е���,to��ҩ�߳�����
			
			Common common = new Common();
			common.bulidts();
			view.update();
			return null;
		}
	
}
