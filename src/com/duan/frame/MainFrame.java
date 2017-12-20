package com.duan.frame;

import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;

import com.duan.bean.ChildPage;
import com.duan.frame.panel.FishPanel;
import com.duan.frame.panel.InputPanel;
import com.duan.frame.panel.OptionMenu;
import com.duan.frame.panel.TaskPanel;
import com.duan.frame.panel.UrlSearchPanel;
import com.duan.parent.PageFrame;
import com.duan.utils.Constant;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private PageFrame pageFrame;
	private ClearFrame clearFrame;
	private SplitFrame splitFrame;
	private TaskPanel taskJPanel;
	private FishPanel fishJPanel;
	private UrlSearchPanel uPanel;
	private OptionMenu optionMenu;
	private InputPanel inputPanel;
	private Map< URL,ChildPage> waitDownLoadChildPage;
	private List<URL> waitDownLoadUrls;
	private int taskNumber;
	private List<String> finishedFilesPath;
	private String savePath;
	private boolean uPanelIsHide;

	public MainFrame() {
		setLayout(null);
		savePath = Constant.SAVE_PATH;
		finishedFilesPath = new CopyOnWriteArrayList<String>();
		setWaitDownLoadChildPage(new HashMap< URL,ChildPage>());
		setWaitDownLoadUrls(new CopyOnWriteArrayList<URL>());
		optionMenu = new OptionMenu(this);
		optionMenu.setLocation(0, 0);
		taskJPanel = new TaskPanel(this);
		taskJPanel.setLocation(0, 55);
		inputPanel = new InputPanel(this);
		inputPanel.setLocation(12, 20);
		fishJPanel = new FishPanel(this);
		fishJPanel.setLocation(
				0,
				taskJPanel.getHeight() + optionMenu.getHeight()
						+ inputPanel.getHeight());
		add(inputPanel);
		add(optionMenu);
		add(taskJPanel);
		add(fishJPanel);
		setSize(406, 830);
		setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2
				- getWidth() / 2,
				Toolkit.getDefaultToolkit().getScreenSize().height / 2
						- getHeight() / 2);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	public InputPanel getInputPanel() {
		return inputPanel;
	}

	public void setInputPanel(InputPanel inputPanel) {
		this.inputPanel = inputPanel;
	}

	public TaskPanel getTaskJPanel() {
		return taskJPanel;
	}

	public void setTaskJPanel(TaskPanel taskJPanel) {
		this.taskJPanel = taskJPanel;
	}

	public FishPanel getFishJPanel() {
		return fishJPanel;
	}

	public void setFishJPanel(FishPanel fishJPanel) {
		this.fishJPanel = fishJPanel;
	}

	public static void main(String[] args) {
		new MainFrame();
	}

	/**
	 * 添加upanel
	 * @param parseName
	 */
	public void addUPanel(String parseName) {
		if (uPanel == null) {
			uPanel = new UrlSearchPanel(this, parseName);
			uPanel.setLocation(406, 21);
			setSize(812, this.getHeight());
			add(uPanel);
			JMenu parseNameMenu = new JMenu(parseName);
			parseNameMenu.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					super.mouseClicked(e);
					if (uPanelIsHide) {
						showUPanel();

					} else {
						hideUPanel();
					}
				}
			});
			optionMenu.setParseNaMenu(parseNameMenu);
			parseNameMenu.setSize(50, 20);
			parseNameMenu.setLocation(350, 0);
			optionMenu.add(parseNameMenu);
		} else {
			uPanel.setParseName(parseName);
			optionMenu.getParseNaMenu().setName(parseName);
			optionMenu.getParseNaMenu().setText(parseName);
		}
		taskJPanel.getMsgArea().append("执行解析器：" + parseName + "\r\n");
	}

	public UrlSearchPanel getuPanel() {
		return uPanel;
	}

	public void setuPanel(UrlSearchPanel uPanel) {
		this.uPanel = uPanel;
	}

	public void removeUPanel() {
		uPanel = null;
		hideUPanel();
	}

	public void hideUPanel() {
		setSize(406, this.getHeight());
		uPanelIsHide = true;
	}

	public void showUPanel() {
		setSize(812, this.getHeight());
		uPanelIsHide = false;
	}

	public PageFrame getPageFrame() {
		return pageFrame;
	}

	public void setPageFrame(PageFrame pageFrame) {
		this.pageFrame = pageFrame;
	}

	public ClearFrame getClearFrame() {
		return clearFrame;
	}

	public void setClearFrame(ClearFrame clearFrame) {
		this.clearFrame = clearFrame;
	}

	public synchronized void addTaskNumber(int size) {
		taskNumber += size;		
	}

	public List<URL> getWaitDownLoadUrls() {
		return waitDownLoadUrls;
	}

	public void setWaitDownLoadUrls(List<URL> waitDownLoadUrls) {
		this.waitDownLoadUrls = waitDownLoadUrls;
	}

	public Map< URL,ChildPage> getWaitDownLoadChildPage() {
		return waitDownLoadChildPage;
	}

	public void setWaitDownLoadChildPage(
			Map< URL,ChildPage> waitDownLoadChildPage) {
		this.waitDownLoadChildPage = waitDownLoadChildPage;
	}

	public synchronized int getTaskNumber() {
		return taskNumber;
	}

	public synchronized void setTaskNumber(int taskNumber) {
		this.taskNumber = taskNumber;
	}

	public List<String> getFinishedFilesPath() {
		return finishedFilesPath;
	}

	public void setFinishedFilesPath(List<String> finishedFilesPath) {
		this.finishedFilesPath = finishedFilesPath;
	}

	public String getSavePath() {
		return savePath;
	}

	public synchronized void delOneTask() {
		if (taskNumber > 0) {
			taskNumber--;
		}
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public double getProgress() {
		if (taskNumber != 0) {
			return (double) finishedFilesPath.size() / (double) taskNumber;
		} else {
			return 0;
		}
	}

	public boolean isFinishDown() {
		if (finishedFilesPath.size() != 0 && finishedFilesPath.size() == taskNumber) {
			return true;
		}
		return false;
	}

	public SplitFrame getSplitFrame() {
		return splitFrame;
	}

	public void setSplitFrame(SplitFrame splitFrame) {
		this.splitFrame = splitFrame;
	}

}
