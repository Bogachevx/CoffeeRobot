namespace CoffeeRobot_PC
{
    partial class MainWindow
    {
        /// <summary>
        /// Обязательная переменная конструктора.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Освободить все используемые ресурсы.
        /// </summary>
        /// <param name="disposing">истинно, если управляемый ресурс должен быть удален; иначе ложно.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Код, автоматически созданный конструктором форм Windows

        /// <summary>
        /// Требуемый метод для поддержки конструктора — не изменяйте 
        /// содержимое этого метода с помощью редактора кода.
        /// </summary>
        private void InitializeComponent()
        {
            this.label1 = new System.Windows.Forms.Label();
            this.IP = new System.Windows.Forms.TextBox();
            this.buttonConnect = new System.Windows.Forms.Button();
            this.buttonReset = new System.Windows.Forms.Button();
            this.Log = new System.Windows.Forms.TextBox();
            this.buttonRun = new System.Windows.Forms.Button();
            this.buttonSocket = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(12, 9);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(20, 13);
            this.label1.TabIndex = 0;
            this.label1.Text = "IP:";
            // 
            // IP
            // 
            this.IP.Location = new System.Drawing.Point(15, 25);
            this.IP.Name = "IP";
            this.IP.Size = new System.Drawing.Size(115, 20);
            this.IP.TabIndex = 1;
            this.IP.Text = "192.168.0.2";
            this.IP.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // buttonConnect
            // 
            this.buttonConnect.Location = new System.Drawing.Point(15, 51);
            this.buttonConnect.Name = "buttonConnect";
            this.buttonConnect.Size = new System.Drawing.Size(115, 46);
            this.buttonConnect.TabIndex = 2;
            this.buttonConnect.Text = "Connect";
            this.buttonConnect.UseVisualStyleBackColor = true;
            this.buttonConnect.Click += new System.EventHandler(this.ButtonConnect_Click);
            // 
            // buttonReset
            // 
            this.buttonReset.Location = new System.Drawing.Point(15, 182);
            this.buttonReset.Name = "buttonReset";
            this.buttonReset.Size = new System.Drawing.Size(115, 73);
            this.buttonReset.TabIndex = 3;
            this.buttonReset.Text = "Reset collision error";
            this.buttonReset.UseVisualStyleBackColor = true;
            this.buttonReset.Click += new System.EventHandler(this.ButtonReset_Click);
            // 
            // Log
            // 
            this.Log.Location = new System.Drawing.Point(136, 9);
            this.Log.Multiline = true;
            this.Log.Name = "Log";
            this.Log.Size = new System.Drawing.Size(264, 343);
            this.Log.TabIndex = 4;
            // 
            // buttonRun
            // 
            this.buttonRun.Location = new System.Drawing.Point(15, 103);
            this.buttonRun.Name = "buttonRun";
            this.buttonRun.Size = new System.Drawing.Size(115, 73);
            this.buttonRun.TabIndex = 5;
            this.buttonRun.Text = "Run program";
            this.buttonRun.UseVisualStyleBackColor = true;
            this.buttonRun.Click += new System.EventHandler(this.ButtonRun_Click);
            // 
            // buttonSocket
            // 
            this.buttonSocket.Location = new System.Drawing.Point(15, 279);
            this.buttonSocket.Name = "buttonSocket";
            this.buttonSocket.Size = new System.Drawing.Size(115, 73);
            this.buttonSocket.TabIndex = 6;
            this.buttonSocket.Text = "Close Socket";
            this.buttonSocket.UseVisualStyleBackColor = true;
            this.buttonSocket.Click += new System.EventHandler(this.ButtonSocket_Click);
            // 
            // MainWindow
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(405, 360);
            this.Controls.Add(this.buttonSocket);
            this.Controls.Add(this.buttonRun);
            this.Controls.Add(this.Log);
            this.Controls.Add(this.buttonReset);
            this.Controls.Add(this.buttonConnect);
            this.Controls.Add(this.IP);
            this.Controls.Add(this.label1);
            this.Name = "MainWindow";
            this.Text = "CoffeeRobot";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox IP;
        private System.Windows.Forms.Button buttonConnect;
        private System.Windows.Forms.Button buttonReset;
        private System.Windows.Forms.TextBox Log;
        private System.Windows.Forms.Button buttonRun;
        private System.Windows.Forms.Button buttonSocket;
    }
}

