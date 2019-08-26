using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using KRcc;

namespace CoffeeRobot_PC
{
    public partial class MainWindow : Form
    {

        Commu com;
        String resp;
        const String ERESET1 = "ereset 1:";
        const String ERESET2 = "ereset 2:";
        const String ZPOW = "zpow on";
        const String PCEXEC_COFFEE = "pcexec 3: coffeerobotpc";
        const String COFFEEMADE = "coffeemade = true";
        const String CLOSESOCKET = "pcexec 3: close_socket";
        private void WriteToLog(String msg)
        {
            if (resp != "")
            {
                Log.Text += "[" + DateTime.Now.ToString("HH:mm:ss") + "]: " + msg + Environment.NewLine;
            }
        }

        public MainWindow()
        {
            InitializeComponent();
        }

        private void ButtonConnect_Click(object sender, EventArgs e)
        {
            try
            {
                com = new Commu("TCP as@" + IP.Text);

                if (com.IsConnected)
                {
                    WriteToLog("Connected\n");
                }
                else
                {
                    WriteToLog("Unable to connect to the robot\n");
                }
            }
            catch (Exception exp)
            {
                WriteToLog("Unable to connect to the robot\n");
            }
            resp = com.command("print \"Hello\"")[1].ToString();
            WriteToLog(resp);
        }

        private void ButtonRun_Click(object sender, EventArgs e)
        {
            resp = com.command(ERESET1)[1].ToString();
            WriteToLog(resp);
            resp = com.command(ERESET2)[1].ToString();
            WriteToLog(resp);
            resp = com.command(ZPOW)[1].ToString();
            WriteToLog(resp);
            resp = com.command(PCEXEC_COFFEE)[1].ToString();
            WriteToLog(resp);

        }

        private void ButtonReset_Click(object sender, EventArgs e)
        {
            resp = com.command(ERESET1)[1].ToString();
            WriteToLog(resp);
            resp = com.command(ERESET2)[1].ToString();
            WriteToLog(resp);
            resp = com.command(ZPOW)[1].ToString();
            WriteToLog(resp);
            resp = com.command(COFFEEMADE)[1].ToString();
            WriteToLog(resp);
        }

        private void ButtonSocket_Click(object sender, EventArgs e)
        {
            resp = com.command(CLOSESOCKET)[1].ToString();
            WriteToLog(resp);
        }
    }
}
