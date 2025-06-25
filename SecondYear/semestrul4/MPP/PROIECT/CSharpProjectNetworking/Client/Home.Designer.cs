using System.ComponentModel;

namespace Client;

partial class Home
{
    /// <summary>
    /// Required designer variable.
    /// </summary>
    private IContainer components = null;

    /// <summary>
    /// Clean up any resources being used.
    /// </summary>
    /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
    protected override void Dispose(bool disposing)
    {
        if (disposing && (components != null))
        {
            components.Dispose();
        }

        base.Dispose(disposing);
    }

    #region Windows Form Designer generated code

    /// <summary>
    /// Required method for Designer support - do not modify
    /// the contents of this method with the code editor.
    /// </summary>
    private void InitializeComponent()
    {
        this.tabControl1 = new System.Windows.Forms.TabControl();
        this.buttonLogout = new System.Windows.Forms.Button();
        this.tabPage1 = new System.Windows.Forms.TabPage();
        this.tabPage2 = new System.Windows.Forms.TabPage();
        this.agecombofilter = new System.Windows.Forms.ComboBox();
        this.label2 = new System.Windows.Forms.Label();
        this.eventcombofilter = new System.Windows.Forms.ComboBox();
        this.label1 = new System.Windows.Forms.Label();
        this.tabPage3 = new System.Windows.Forms.TabPage();
        this.registerbutton = new System.Windows.Forms.Button();
        this.agecomboregister = new System.Windows.Forms.ComboBox();
        this.label7 = new System.Windows.Forms.Label();
        this.eventcomboregister2 = new System.Windows.Forms.ComboBox();
        this.eventcomboregister1 = new System.Windows.Forms.ComboBox();
        this.label6 = new System.Windows.Forms.Label();
        this.label5 = new System.Windows.Forms.Label();
        this.textCNP = new System.Windows.Forms.TextBox();
        this.textName = new System.Windows.Forms.TextBox();
        this.label4 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.eventstable = new System.Windows.Forms.DataGridView();
        this.participantstable = new System.Windows.Forms.DataGridView();
        this.tabControl1.SuspendLayout();
        this.tabPage1.SuspendLayout();
        this.tabPage2.SuspendLayout();
        this.tabPage3.SuspendLayout();
        ((System.ComponentModel.ISupportInitialize)(this.eventstable)).BeginInit();
        ((System.ComponentModel.ISupportInitialize)(this.participantstable)).BeginInit();
        this.SuspendLayout();
        // 
        // tabControl1
        // 
        this.tabControl1.Controls.Add(this.tabPage1);
        this.tabControl1.Controls.Add(this.tabPage2);
        this.tabControl1.Controls.Add(this.tabPage3);
        this.tabControl1.Location = new System.Drawing.Point(24, 24);
        this.tabControl1.Name = "tabControl1";
        this.tabControl1.SelectedIndex = 0;
        this.tabControl1.Size = new System.Drawing.Size(764, 382);
        this.tabControl1.TabIndex = 0;

        // 
        // tabPage1
        // 
        this.tabPage1.Controls.Add(this.eventstable);
        this.tabPage1.Location = new System.Drawing.Point(4, 25);
        this.tabPage1.Name = "tabPage1";
        this.tabPage1.Padding = new System.Windows.Forms.Padding(3);
        this.tabPage1.Size = new System.Drawing.Size(756, 353);
        this.tabPage1.TabIndex = 0;
        this.tabPage1.Text = "Show Participants";
        this.tabPage1.UseVisualStyleBackColor = true;
        // 
        // tabPage2
        // 
        this.tabPage2.Controls.Add(this.participantstable);
        this.tabPage2.Controls.Add(this.agecombofilter);
        this.tabPage2.Controls.Add(this.label2);
        this.tabPage2.Controls.Add(this.eventcombofilter);
        this.tabPage2.Controls.Add(this.label1);
        this.tabPage2.Location = new System.Drawing.Point(4, 25);
        this.tabPage2.Name = "tabPage2";
        this.tabPage2.Padding = new System.Windows.Forms.Padding(3);
        this.tabPage2.Size = new System.Drawing.Size(756, 353);
        this.tabPage2.TabIndex = 1;
        this.tabPage2.Text = "Filter Participants";
        this.tabPage2.UseVisualStyleBackColor = true;
        // 
        // agecombofilter
        // 
        this.agecombofilter.FormattingEnabled = true;
        this.agecombofilter.Location = new System.Drawing.Point(344, 13);
        this.agecombofilter.Name = "agecombofilter";
        this.agecombofilter.Size = new System.Drawing.Size(121, 24);
        this.agecombofilter.TabIndex = 3;
        // 
        // label2
        // 
        this.label2.AutoSize = true;
        this.label2.Location = new System.Drawing.Point(262, 16);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(76, 16);
        this.label2.TabIndex = 2;
        this.label2.Text = "Age range: ";
        // 
        // eventcombofilter
        // 
        this.eventcombofilter.FormattingEnabled = true;
        this.eventcombofilter.Location = new System.Drawing.Point(74, 13);
        this.eventcombofilter.Name = "eventcombofilter";
        this.eventcombofilter.Size = new System.Drawing.Size(121, 24);
        this.eventcombofilter.TabIndex = 1;
        // 
        // label1
        // 
        this.label1.AutoSize = true;
        this.label1.Location = new System.Drawing.Point(21, 16);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(47, 16);
        this.label1.TabIndex = 0;
        this.label1.Text = "Event: ";
        // 
        // tabPage3
        // 
        this.tabPage3.Controls.Add(this.registerbutton);
        this.tabPage3.Controls.Add(this.buttonLogout);
        this.tabPage3.Controls.Add(this.agecomboregister);
        this.tabPage3.Controls.Add(this.label7);
        this.tabPage3.Controls.Add(this.eventcomboregister2);
        this.tabPage3.Controls.Add(this.eventcomboregister1);
        this.tabPage3.Controls.Add(this.label6);
        this.tabPage3.Controls.Add(this.label5);
        this.tabPage3.Controls.Add(this.textCNP);
        this.tabPage3.Controls.Add(this.textName);
        this.tabPage3.Controls.Add(this.label4);
        this.tabPage3.Controls.Add(this.label3);
        this.tabPage3.Location = new System.Drawing.Point(4, 25);
        this.tabPage3.Name = "tabPage3";
        this.tabPage3.Padding = new System.Windows.Forms.Padding(3);
        this.tabPage3.Size = new System.Drawing.Size(756, 353);
        this.tabPage3.TabIndex = 2;
        this.tabPage3.Text = "Register Participants";
        this.tabPage3.UseVisualStyleBackColor = true;
        // 
        // registerbutton
        // 
        this.registerbutton.BackColor = System.Drawing.Color.Plum;
        this.registerbutton.Location = new System.Drawing.Point(460, 201);
        this.registerbutton.Name = "registerbutton";
        this.registerbutton.Size = new System.Drawing.Size(126, 32);
        this.registerbutton.TabIndex = 10;
        this.registerbutton.Text = "Register";
        this.registerbutton.UseVisualStyleBackColor = false;
        this.registerbutton.Click += new System.EventHandler(this.button1_Click);

        this.buttonLogout.BackColor = System.Drawing.Color.DarkSlateGray;
        this.buttonLogout.Size = new System.Drawing.Size(126, 32);
        this.buttonLogout.Text = "Logout";
        this.buttonLogout.UseVisualStyleBackColor = false;
        this.buttonLogout.Location = new System.Drawing.Point(460, 301);
        this.buttonLogout.Name = "buttonLogout";
        this.buttonLogout.Click += new System.EventHandler(this.button_Logout);

        // 
        // agecomboregister
        // 
        this.agecomboregister.FormattingEnabled = true;
        this.agecomboregister.Location = new System.Drawing.Point(134, 173);
        this.agecomboregister.Name = "agecomboregister";
        this.agecomboregister.Size = new System.Drawing.Size(121, 24);
        this.agecomboregister.TabIndex = 9;
        // 
        // label7
        // 
        this.label7.AutoSize = true;
        this.label7.Location = new System.Drawing.Point(55, 176);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(73, 16);
        this.label7.TabIndex = 8;
        this.label7.Text = "Age range:";
        // 
        // eventcomboregister2
        // 
        this.eventcomboregister2.FormattingEnabled = true;
        this.eventcomboregister2.Location = new System.Drawing.Point(485, 100);
        this.eventcomboregister2.Name = "eventcomboregister2";
        this.eventcomboregister2.Size = new System.Drawing.Size(121, 24);
        this.eventcomboregister2.TabIndex = 7;
        // 
        // eventcomboregister1
        // 
        this.eventcomboregister1.FormattingEnabled = true;
        this.eventcomboregister1.Location = new System.Drawing.Point(115, 105);
        this.eventcomboregister1.Name = "eventcomboregister1";
        this.eventcomboregister1.Size = new System.Drawing.Size(121, 24);
        this.eventcomboregister1.TabIndex = 6;
        // 
        // label6
        // 
        this.label6.AutoSize = true;
        this.label6.Location = new System.Drawing.Point(366, 108);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(113, 16);
        this.label6.TabIndex = 5;
        this.label6.Text = "Event2 (optional) :";
        // 
        // label5
        // 
        this.label5.AutoSize = true;
        this.label5.Location = new System.Drawing.Point(55, 108);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(54, 16);
        this.label5.TabIndex = 4;
        this.label5.Text = "Event1: ";
        // 
        // textCNP
        // 
        this.textCNP.Location = new System.Drawing.Point(413, 23);
        this.textCNP.Name = "textCNP";
        this.textCNP.Size = new System.Drawing.Size(246, 22);
        this.textCNP.TabIndex = 3;
        // 
        // textName
        // 
        this.textName.Location = new System.Drawing.Point(110, 23);
        this.textName.Name = "textName";
        this.textName.Size = new System.Drawing.Size(201, 22);
        this.textName.TabIndex = 2;
        // 
        // label4
        // 
        this.label4.AutoSize = true;
        this.label4.Location = new System.Drawing.Point(366, 26);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(41, 16);
        this.label4.TabIndex = 1;
        this.label4.Text = "CNP: ";
        // 
        // label3
        // 
        this.label3.AutoSize = true;
        this.label3.Location = new System.Drawing.Point(54, 26);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(50, 16);
        this.label3.TabIndex = 0;
        this.label3.Text = "Name: ";
        // 
        // eventstable
        // 
        this.eventstable.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
        this.eventstable.Location = new System.Drawing.Point(32, 27);
        this.eventstable.Name = "eventstable";
        this.eventstable.RowHeadersWidth = 51;
        this.eventstable.RowTemplate.Height = 24;
        this.eventstable.Size = new System.Drawing.Size(684, 298);
        this.eventstable.TabIndex = 0;
        // 
        // participantstable
        // 
        this.participantstable.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
        this.participantstable.Location = new System.Drawing.Point(32, 64);
        this.participantstable.Name = "participantstable";
        this.participantstable.RowHeadersWidth = 51;
        this.participantstable.RowTemplate.Height = 24;
        this.participantstable.Size = new System.Drawing.Size(693, 249);
        this.participantstable.TabIndex = 4;
        // 
        // Home
        // 
        this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(800, 450);
        this.Controls.Add(this.tabControl1);
        this.Name = "Home";
        this.Text = "Home";
        this.tabControl1.ResumeLayout(false);
        this.tabPage1.ResumeLayout(false);
        this.tabPage2.ResumeLayout(false);
        this.tabPage2.PerformLayout();
        this.tabPage3.ResumeLayout(false);
        this.tabPage3.PerformLayout();
        ((System.ComponentModel.ISupportInitialize)(this.eventstable)).EndInit();
        ((System.ComponentModel.ISupportInitialize)(this.participantstable)).EndInit();
        this.ResumeLayout(false);

    }

    #endregion

    private System.Windows.Forms.TabControl tabControl1;
    private System.Windows.Forms.TabPage tabPage1;
    private System.Windows.Forms.TabPage tabPage2;
    private System.Windows.Forms.TabPage tabPage3;
    private System.Windows.Forms.ComboBox agecombofilter;
    private System.Windows.Forms.Label label2;
    private System.Windows.Forms.ComboBox eventcombofilter;
    private System.Windows.Forms.Label label1;
    private System.Windows.Forms.TextBox textCNP;
    private System.Windows.Forms.TextBox textName;
    private System.Windows.Forms.Label label4;
    private System.Windows.Forms.Label label3;
    private System.Windows.Forms.Button registerbutton;
    private System.Windows.Forms.ComboBox agecomboregister;
    private System.Windows.Forms.Label label7;
    private System.Windows.Forms.ComboBox eventcomboregister2;
    private System.Windows.Forms.ComboBox eventcomboregister1;
    private System.Windows.Forms.Label label6;
    private System.Windows.Forms.Label label5;
    private System.Windows.Forms.DataGridView eventstable;
    private System.Windows.Forms.DataGridView participantstable;
    private System.Windows.Forms.Button buttonLogout;
}