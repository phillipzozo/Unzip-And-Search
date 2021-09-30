package controller;

import java.awt.Desktop;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import model.Model_main;
import view.ViewPageCompressed;
import view.ViewPageIllegal;
import view.ViewPagePrograming;
import view.ViewPageReport;
import view.ViewPageSelect;
import view.View_main;

public class Controller_main implements ActionListener{
	private Model_main model_main;
	private View_main view_main;

	private ViewPageSelect panel_right_selectfile;
	private ViewPageCompressed panel_right_compressed;
	private ViewPageIllegal panel_right_illegal;
	private ViewPagePrograming panel_right_programing;
	private ViewPageReport panel_right_report;
	
	private ArrayList<JPanel> LeftSteps = new ArrayList<JPanel>();
	private ArrayList<Object> RightPages = new ArrayList<Object>();
	private int PageIndex = 0;
	
	private String source_path;
	private String compressed_format;
	private boolean auto_decompress;
	private String illegal_format;

	private String path_folder_config = ".\\config";
	private String path_folder_results = ".\\results";
	private String path_file_compressed_format = path_folder_config+ "\\compressed_format.txt";
	private String path_file_illegal_format = path_folder_config+ "\\illegal_format.txt";
	
	private ExecutorService programeAndWatchDog = Executors.newCachedThreadPool(); //newSingleThreadExecutor
	private Future<?> ExecutionFuture;
	
	//Constructor
	public Controller_main() {
		model_main = new Model_main();
		view_main = new View_main();
		
		view_main.setVisible(true);
		
		model_main.modelFile.FolderAndFileCheck(0, path_folder_config);
		model_main.modelFile.FolderAndFileCheck(0, path_folder_results);
		model_main.modelFile.FolderAndFileCheck(1, path_file_compressed_format);
		model_main.modelFile.FolderAndFileCheck(1, path_file_illegal_format);
		
		
		/*** left steps ***/
		LeftSteps.add(view_main.getLeftSelectfile());
		LeftSteps.add(view_main.getLeftCompressfomat());
		LeftSteps.add(view_main.getLeftIllegal());
		LeftSteps.add(view_main.getLeftProcessing());
		LeftSteps.add(view_main.getLeftReport());
		
		/*** right pages ***/
		panel_right_selectfile = (ViewPageSelect) view_main.getRightSelectfile();
		panel_right_compressed = (ViewPageCompressed) view_main.getRightCompressfomat();
		panel_right_illegal = (ViewPageIllegal) view_main.getRightIllegal();
		panel_right_programing = (ViewPagePrograming) view_main.getRightProcessing();
		panel_right_report = (ViewPageReport) view_main.getRightReport();
		RightPages.add(panel_right_selectfile);
		RightPages.add(panel_right_compressed);
		RightPages.add(panel_right_illegal);
		RightPages.add(panel_right_programing);
		RightPages.add(panel_right_report);
		
		refreshStepAndPage();
		
		/*** right page button function ***/
        //Set Action Commands
		view_main.getBtnClose().setActionCommand("CLOSE");
		view_main.getBtnMinimize().setActionCommand("MINIMIZE");
		panel_right_selectfile.getBtnSelectFile().setActionCommand("SELETFILE");
		panel_right_selectfile.getBtnNext().setActionCommand("NEXT");
		panel_right_compressed.getBtnPrevious().setActionCommand("PREVIOUS");
		panel_right_compressed.getBtnNext().setActionCommand("NEXT");
		panel_right_illegal.getBtnPrevious().setActionCommand("PREVIOUS");
		panel_right_illegal.getBtnFinish().setActionCommand("FINISH");
		panel_right_programing.getBtnNext().setActionCommand("NEXT");
		panel_right_report.getBtnOpenReport().setActionCommand("OPENREPORT");
		panel_right_report.getBtnExit().setActionCommand("EXIT");
        //Add Action Listener
		view_main.getBtnClose().addActionListener(this);
		view_main.getBtnMinimize().addActionListener(this);
		panel_right_selectfile.getBtnSelectFile().addActionListener(this);
		panel_right_selectfile.getBtnNext().addActionListener(this);
		panel_right_compressed.getBtnPrevious().addActionListener(this);
		panel_right_compressed.getBtnNext().addActionListener(this);
		panel_right_illegal.getBtnPrevious().addActionListener(this);
		panel_right_illegal.getBtnFinish().addActionListener(this);
		panel_right_programing.getBtnNext().addActionListener(this);
		panel_right_report.getBtnOpenReport().addActionListener(this);
		panel_right_report.getBtnExit().addActionListener(this);
		
		enableDragAndDrop(panel_right_selectfile);
		
		
		init();
	}
	
	private void init() {
		compressed_format = model_main.modelFile.readFile(path_file_compressed_format);
		illegal_format = model_main.modelFile.readFile(path_file_illegal_format);
		panel_right_compressed.setConditions(compressed_format);
		panel_right_illegal.setConditions(illegal_format);
	}

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
	        case "EXIT":
            case "CLOSE":
            	view_main.dispose();
		    	System.exit(0);
                break;
            case "MINIMIZE":
            	view_main.setExtendedState(JFrame.ICONIFIED);
                break;
            case "PREVIOUS":
            	PageIndex -= 1;
            	sleep(100);
            	refreshStepAndPage();
                break;
            case "FINISH":
            	compressed_format = panel_right_compressed.getConditions();
            	auto_decompress = panel_right_compressed.isAutoDecompress();
            	illegal_format = panel_right_illegal.getConditions();
            	model_main.modelFile.updateFile(path_file_compressed_format, compressed_format);
            	model_main.modelFile.updateFile(path_file_illegal_format, illegal_format);

            	PageIndex += 1;
            	refreshStepAndPage();
            	sleep(100);
            	
            	panel_right_programing.getBtnNext().setVisible(false);
            	
            	final Future<?> FuturePrograming =
        			programeAndWatchDog.submit(new Thread(new Runnable() {
                    @Override
                    public void run() {
                    	//PrintStream standardOut = System.out;
                    	
                    	JTextArea textArea = panel_right_programing.getTextarea();
                    	PrintStream printStream = new PrintStream(new TextAreaOutputStream(textArea));
                    	System.setOut(printStream);
                    	System.setErr(printStream);
                    	try {
                    		model_main.execute(source_path, compressed_format, auto_decompress, illegal_format);
                    	}catch(Exception e) {
                    		e.printStackTrace();
                    	}
                    }
	           	}) {});
            	
            	final Future<?> FutureWatchPrograming =
        			programeAndWatchDog.submit(new Thread(new Runnable() {
                    @Override
                    public void run() {
                    	while(true) {
                    		if (FuturePrograming.isCancelled() ||
        						FuturePrograming.isDone() ||
        			            programeAndWatchDog.isShutdown() ||
        						programeAndWatchDog.isTerminated()) {

        						programeAndWatchDog.shutdown();
        						model_main.saveLog(panel_right_programing.getTextarea().getText());
        						panel_right_programing.getBtnNext().setVisible(true);
        						System.out.println("Completed!");
        						panel_right_programing.program_completed();
        						
        						
                        		panel_right_report.setItemStatusList(model_main.getReportItems(), model_main.getReportItemsStatus());
        						break;
        					}
                    	}
                    }
	           	}) {});
            	
                break;
            case "NEXT":
            	PageIndex += 1;
            	sleep(100);
            	refreshStepAndPage();
                break;
            case "OPENREPORT":
				try {
					Desktop.getDesktop().open(new File(model_main.getDestinationPath()));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
                break;
            case "SELETFILE":
				try {
					Desktop.getDesktop().open(new File(".\\"));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
            	//chooseFile();
                break;
        }
    }
    
    
    /* other functions */
    private void sleep(int time) {
    	try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
	
	public void refreshStepAndPage() {
		view_main.setLeftStepDefault(LeftSteps);
		view_main.setLeftStepActivate(LeftSteps.get(PageIndex));
		//System.out.print("LeftSteps.get("+ PageIndex+ "): "+ LeftSteps.get(PageIndex));
		
		view_main.setRIghtPageHidden(RightPages);
		view_main.setRIghtPageShow(RightPages.get(PageIndex));
		//System.out.print("RightPages.get("+ PageIndex+ "): "+ RightPages.get(PageIndex));
	}
    
    private void chooseFile() {
    	JFileChooser fileChooser = new JFileChooser();
    	fileChooser.setCurrentDirectory(new File(".\\"));
    	fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
    	int result = fileChooser.showOpenDialog(new JDialog());
    	if (result == JFileChooser.APPROVE_OPTION) {
    	    File selectedFile = fileChooser.getSelectedFile();
    	    //System.out.println("Selected file: " + selectedFile.getAbsolutePath());
    	    source_path = selectedFile.getAbsolutePath();
    	    
        	panel_right_selectfile.setTextPath(source_path);
    	}
    }
    
    // Refer: https://www.specialagentsqueaky.com/blog-post/mbu5p27a/2011-01-09-drag-and-dropping-files-to-java-desktop-application/
    private void enableDragAndDrop(JComponent j){
        DropTarget target = new DropTarget(j, new DropTargetListener(){
        	
            public void dragEnter(DropTargetDragEvent event){
            }
            
            public void dragExit(DropTargetEvent event){
            }
            
            public void dragOver(DropTargetDragEvent event){
            }
            
            public void dropActionChanged(DropTargetDragEvent event){
            }
            
            public void drop(DropTargetDropEvent event){
                // Accept the drop first, important!
            	event.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
            	
            	// Get the transfer which can provide the dropped item data
                Transferable transferable = event.getTransferable();
                
            	// Get the data formats of the dropped item
            	DataFlavor[] flavors = transferable.getTransferDataFlavors();
            	
            	// Loop through the flavors
                for (DataFlavor flavor : flavors) {
                    try {
                        // If the drop items are files
                        if (flavor.isFlavorJavaFileListType()) {

                            // Get all of the dropped files
                        	List<?> files = (List<?>) transferable.getTransferData(flavor);
                        	
                        	File file = (File)files.get(0);
                        	//System.out.println("File path is '" + file.getPath() + "'.");
                        	source_path = file.getPath();

                        	panel_right_selectfile.setTextPath(source_path);
                        	
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                // Inform that the drop is complete
                event.dropComplete(true);
            }
        });
    }
    
    
    public class TextAreaOutputStream extends OutputStream {
        private JTextArea textControl;

        /**
         * Creates a new instance of TextAreaOutputStream which writes
         * to the specified instance of javax.swing.JTextArea control.
         *
         * @param control   A reference to the javax.swing.JTextArea
         *                  control to which the output must be redirected
         *                  to.
         */
        public TextAreaOutputStream( JTextArea control ) {
            textControl = control;
        }

        /**
         * Writes the specified byte as a character to the
         * javax.swing.JTextArea.
         *
         * @param   b   The byte to be written as character to the
         *              JTextArea.
         */
        public void write( int b ) throws IOException {
            // append the data as characters to the JTextArea control
            textControl.append( String.valueOf( ( char )b ) );
            textControl.setCaretPosition(textControl.getDocument().getLength());
        }  
    }
}
