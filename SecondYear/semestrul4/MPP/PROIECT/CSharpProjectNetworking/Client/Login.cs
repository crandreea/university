using Model;
using Server;
using Services;
using System;
using System.Windows.Forms;

namespace Client
{
    public partial class Login : Form
    {
        public Home mainForm;
        private Organizator loggedUser;
        private IProjectServices server;

        public void SetServer(IProjectServices s)
        {
            this.server = s;
        }

        public void SetMainForm(Home p)
        {
            this.mainForm = p;
        }
        
        public Login()
        {
            InitializeComponent();
            this.button1.Click += new EventHandler(this.LoginButton_Click);
            this.textBox2.PasswordChar = '*';
        }

        private void LoginButton_Click(object sender, EventArgs e)
        {
            string username = textBox1.Text;
            string password = textBox2.Text;
            try
            {
                loggedUser = server.Login(username, password, mainForm);

                if (loggedUser != null)
                {
                    MessageBox.Show("Welcome, " + loggedUser.Username + "!", 
                        "Succes", MessageBoxButtons.OK, MessageBoxIcon.Information);
                    
                     mainForm.SetUser(loggedUser);
                     mainForm.SetServer(server); 
                    
                     mainForm.Show();
                     this.Hide();
                }
                else
                {
                    MessageBox.Show("Username and password incorrect!", 
                        "Eroare", MessageBoxButtons.OK, MessageBoxIcon.Error);
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show("Authentication error: " + ex.Message, 
                    "Eroare", MessageBoxButtons.OK, MessageBoxIcon.Error);
                Console.Out.WriteLine(ex.Message);
            }
        }
    }
}