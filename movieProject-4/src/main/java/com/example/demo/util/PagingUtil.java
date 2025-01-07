package com.example.demo.util;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PagingUtil {
	private int maxNum;//��ü �� ������ �����ϴ� ����
	private int pageNum;//���� ���̴� �������� ��ȣ ���� ����
	private int listCnt;//�� ������ �� ���� �Խñ��� ���� ���� ����
	private int pageCnt;//������ ������ ��ȣ ���� ���� ����

	//����¡�� html �ڵ带 ����� �޼ҵ�
	public String makePaging() {
		String pageStr = null;
		StringBuffer sb = new StringBuffer();

		//1. ��ü ������ ���� ���ϱ�(������ �� listCnt(10��) ��ŭ ���)
		//��ü �Խñ� 9�� : 1 ������
		//��ü �Խñ� 11�� : 2 ������
		int totalPage = (maxNum % listCnt) > 0 ?
				maxNum / listCnt + 1 :
				maxNum / listCnt;
		//2. ���� �������� ���� �ִ� �׷� ��ȣ ���ϱ�
		int curGroup = (pageNum % pageCnt) > 0 ?
				pageNum / pageCnt + 1 :
				pageNum / pageCnt;
		//3. ���� ���̴� ������ �׷��� ���� ��ȣ ���ϱ�
		int start = (curGroup * pageCnt) - (pageCnt - 1);
		//�ι�° �׷� ���۹�ȣ = pageCnt(5) * 2 - (5 - 1) = 6

		//4. ���� ���̴� ������ �׷��� ������ ��ȣ ���ϱ�
		int end = (curGroup * pageCnt) >= totalPage ?
				totalPage : curGroup * pageCnt;

		//���� ��ư ó��
		if(start != 1) {
			sb.append("<a class='pno' href='./?pageNum="
					+ (start - 1) + "'>");
			sb.append("��</a>");
		}//<a class='pno' herf='./?pageNum=5'> ���� </a>
		//������ ��ȣ�� 5���� �����ִ� ���,
		//6�������� ���̴� ȭ�鿡�� ���� ��ư�� ���̰� �ǰ�
		//�� ���� ��ư�� ��ũ�� 5�������� �ȴ�.

		//�߰� ������ ��ȣ ��ư ó��
		for(int i = start; i <= end; i++) {
			if(pageNum != i) {//���� �������� �ƴ� ������ ��ȣ
				sb.append("<a class='pno' href='./?pageNum="
						+ i + "'>");
				sb.append(i + "</a>");
			}//<a class='pno' href='./?pageNum=3> 3 </a>
			else {//���� ���̴� ������
				//���� ���̴� ������ ��ȣ���� ��ũ�� ���� �ʴ´�.
				sb.append("<font class='pno'>");
				sb.append(i + "</font>");
			}//<font class='pno' style='color: red;'> 2 </font>
		}

		//���� ��ư ó��
		if(end != totalPage) {
			sb.append("<a class='pno' href='./?pageNum="
					+ (end + 1) + "'>");
			sb.append("��</a>");
		}//<a class='pno' href='./?pageNum=6'> ���� </a>

		//StringBuffer�� ����� ������ ���ڿ��� ��ȯ
		pageStr = sb.toString();

		return pageStr;
	}
}//class end
