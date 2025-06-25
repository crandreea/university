using System.ComponentModel;

namespace Client;

partial class Login
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
        this.panel1 = new System.Windows.Forms.Panel();
        this.label1 = new System.Windows.Forms.Label();
        this.button1 = new System.Windows.Forms.Button();
        this.textBox1 = new System.Windows.Forms.TextBox();
        this.textBox2 = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.panel1.SuspendLayout();
        this.SuspendLayout();
        // 
        // panel1
        // 
        this.panel1.BackColor = System.Drawing.SystemColors.ControlLightLight;
        this.panel1.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
        this.panel1.Controls.Add(this.label3);
        this.panel1.Controls.Add(this.label2);
        this.panel1.Controls.Add(this.textBox2);
        this.panel1.Controls.Add(this.textBox1);
        this.panel1.Controls.Add(this.button1);
        this.panel1.Controls.Add(this.label1);
        this.panel1.Location = new System.Drawing.Point(278, 98);
        this.panel1.Name = "panel1";
        this.panel1.Size = new System.Drawing.Size(235, 243);
        this.panel1.TabIndex = 0;
        // 
        // label1
        // 
        this.label1.AutoSize = true;
        this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.label1.Location = new System.Drawing.Point(67, 22);
        this.label1.Name = "label1";
        this.label1.RightToLeft = System.Windows.Forms.RightToLeft.Yes;
        this.label1.Size = new System.Drawing.Size(95, 25);
        this.label1.TabIndex = 0;
        this.label1.Text = "Welcome";
        // 
        // button1
        // 
        this.button1.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(128)))), ((int)(((byte)(128)))), ((int)(((byte)(255)))));
        this.button1.Location = new System.Drawing.Point(67, 184);
        this.button1.Name = "button1";
        this.button1.Size = new System.Drawing.Size(100, 29);
        this.button1.TabIndex = 1;
        this.button1.Text = "Login";
        this.button1.UseVisualStyleBackColor = false;
        // 
        // textBox1
        // 
        this.textBox1.Location = new System.Drawing.Point(41, 83);
        this.textBox1.Name = "textBox1";
        this.textBox1.Size = new System.Drawing.Size(143, 22);
        this.textBox1.TabIndex = 2;
        // 
        // textBox2
        // 
        this.textBox2.Location = new System.Drawing.Point(41, 143);
        this.textBox2.Name = "textBox2";
        this.textBox2.Size = new System.Drawing.Size(143, 22);
        this.textBox2.TabIndex = 3;
        // 
        // label2
        // 
        this.label2.AutoSize = true;
        this.label2.Location = new System.Drawing.Point(41, 64);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(70, 16);
        this.label2.TabIndex = 4;
        this.label2.Text = "Username";
        // 
        // label3
        // 
        this.label3.AutoSize = true;
        this.label3.Location = new System.Drawing.Point(41, 121);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(67, 16);
        this.label3.TabIndex = 5;
        this.label3.Text = "Password";
        // 
        // Form1
        // 
        this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(800, 450);
        this.Controls.Add(this.panel1);
        this.Name = "Form1";
        this.Text = "Form1";
        this.panel1.ResumeLayout(false);
        this.panel1.PerformLayout();
        this.ResumeLayout(false);

    }

    #endregion

    private System.Windows.Forms.Panel panel1;
    private System.Windows.Forms.TextBox textBox2;
    private System.Windows.Forms.TextBox textBox1;
    private System.Windows.Forms.Button button1;
    private System.Windows.Forms.Label label1;
    private System.Windows.Forms.Label label3;
    private System.Windows.Forms.Label label2;
}