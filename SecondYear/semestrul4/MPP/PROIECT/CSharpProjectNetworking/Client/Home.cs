using Model;
using Server;
using Services;
using System.Collections.Generic;
using System.Data;
using System.Windows.Forms;

namespace Client
{
    public partial class Home : Form, IProjectObserver
    {
        private IProjectServices service;
        private Organizator loggedUser;
        public Home()
        {
            InitializeComponent();
            
            PopulateComboBoxes(); 
            InitializeTables();
        
            eventcombofilter.SelectedIndexChanged += new EventHandler(EventComboFilter_SelectedIndexChanged);
            agecombofilter.SelectedIndexChanged += new EventHandler(AgeComboFilter_SelectedIndexChanged);
        } 
        

        public void SetServer(IProjectServices server)
        {
            service = server;
            try
            {
                RefreshEventsTable();
            }
            catch (Exception ex)
            {
                Console.Out.WriteLine(ex);
            }
        }

        public void SetUser(Organizator user)
        {
            loggedUser = user;
        }

        private void InitializeTables()
        {
            eventstable.AutoGenerateColumns = false;
            eventstable.Columns.Add(new DataGridViewTextBoxColumn
            {
                Name = "eventNameColumn",
                HeaderText = "Eveniment",
                DataPropertyName = "EventName"
            });
            eventstable.Columns.Add(new DataGridViewTextBoxColumn
            {
                Name = "ageGroupColumn",
                HeaderText = "Grupă de vârstă",
                DataPropertyName = "AgeGroup"
            });
            eventstable.Columns.Add(new DataGridViewTextBoxColumn
            {
                Name = "registeredCountColumn",
                HeaderText = "Participanți înregistrați",
                DataPropertyName = "RegisteredCount"
            });
            
            
            participantstable.AutoGenerateColumns = false;
            participantstable.Columns.Add(new DataGridViewTextBoxColumn
            {
                Name = "participantNameColumn",
                HeaderText = "Nume",
                DataPropertyName = "Nume"
            });
            participantstable.Columns.Add(new DataGridViewTextBoxColumn
            {
                Name = "participantAgeColumn",
                HeaderText = "Vârstă",
                DataPropertyName = "Varsta"
            });
        }
    
        private void PopulateComboBoxes()
        {
            List<string> events = new List<string> { "Desen", "Cautare de comori", "Poezie" };
            eventcombofilter.DataSource = new List<string>(events);
            eventcomboregister1.DataSource = new List<string>(events);
            events.Add(" ");
            eventcomboregister2.DataSource = new List<string>(events);
            
            List<string> ageGroups = new List<string> { "6-8", "9-11", "12-15" };
            agecombofilter.DataSource = new List<string>(ageGroups);
            agecomboregister.DataSource = new List<string>(ageGroups);
        }
    
        private void RefreshEventsTable()
        {
            if (service != null)
            {
                service.RefreshProbeStatistics();
                IList<ProbaDTO> probaList = service.GetAllProbaDto();
            
                Console.Out.WriteLine(probaList.Count);
                DataTable eventsData = new DataTable();
                eventsData.Columns.Add("EventName", typeof(string));
                eventsData.Columns.Add("AgeGroup", typeof(string));
                eventsData.Columns.Add("RegisteredCount", typeof(int));
            
                foreach (ProbaDTO proba in probaList)
                {
                    Console.Out.WriteLine(proba);
                    eventsData.Rows.Add(
                        proba.GetNumeEveniment(),
                        proba.GetGrupaVarsta(),
                        proba.GetNumarInregistrati()
                    );
                }
            
                eventstable.DataSource = eventsData;
            }
            
        }
    
        private void EventComboFilter_SelectedIndexChanged(object sender, EventArgs e)
        {
            FilterParticipants();
        }
        
        private void AgeComboFilter_SelectedIndexChanged(object sender, EventArgs e)
        {
            FilterParticipants();
        }
    
        private void FilterParticipants()
        {
            string selectedProba = eventcombofilter.SelectedItem?.ToString();
            string selectedCategory = agecombofilter.SelectedItem?.ToString();
            
            if (string.IsNullOrEmpty(selectedProba) || string.IsNullOrEmpty(selectedCategory))
                return;
            
            Proba proba = service.GetProbaByName(selectedProba);
            CategorieVarsta categorieVarsta = service.GetVarstaByRange(selectedCategory);
            IList<ParticipantDTO> results = service.SearchParticipants(proba, categorieVarsta);
            
            DataTable participantsData = new DataTable();
            participantsData.Columns.Add("Nume", typeof(string));
            participantsData.Columns.Add("Varsta", typeof(int));
           
            
            foreach (ParticipantDTO participant in results)
            {
                participantsData.Rows.Add(
                    participant.GetNume(),
                    participant.GetVarsta()
                );
            }
            
            participantstable.DataSource = participantsData;
        }

        private void button1_Click(object sender, EventArgs e)
        {
            string name = textName.Text;
            string cnp = textCNP.Text;
            
            string event1 = eventcomboregister1.SelectedItem?.ToString();
            string event2 = eventcomboregister2.SelectedItem?.ToString();
            
            string age = agecomboregister.SelectedItem?.ToString();
            
            if (string.IsNullOrEmpty(name) || string.IsNullOrEmpty(cnp) || 
                string.IsNullOrEmpty(event1) || string.IsNullOrEmpty(age))
            {
                MessageBox.Show("Field must be completed",
                    "Eroare", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }
            
            string result = service.RegisterParticipant(name, cnp, event1, event2, age);
            
            MessageBox.Show(result, "Registration ", 
                result.Contains("successful") ? MessageBoxButtons.OK : MessageBoxButtons.OK, 
                result.Contains("successful") ? MessageBoxIcon.Information : MessageBoxIcon.Error);
            
            if (result.Contains("successful"))
            {
                textName.Clear();
                textCNP.Clear();
                eventcomboregister1.SelectedIndex = -1;
                eventcomboregister2.SelectedIndex = -1;
                agecomboregister.SelectedIndex = -1;
            
                RefreshEventsTable();
            }
        }
        
        public void InscriereAdded(Inscriere inscriere)
        {
            this.BeginInvoke((MethodInvoker) delegate
            {
                Console.WriteLine("Reservation added: " + inscriere);
                RefreshEventsTable();
            });
        }

        private void button_Logout(object sender, EventArgs e)
        {
            MessageBox.Show("Logout... ", "Succes", MessageBoxButtons.OK, MessageBoxIcon.Information);
            // Logout(); 
            service.Logout(loggedUser, this);
            Login loginForm = new Login();
            loginForm.Show();
            this.Hide();
        }

        Organizator Logout()
        {
            try
            {
                service.Logout(loggedUser, this);
                MessageBox.Show("Logout successful (MainController).");
                return loggedUser;
            }
            catch (ProjectException ex)
            {
                MessageBox.Show("Logout failed (MainController): " + ex.Message);
            }
            return loggedUser;
        }

        
    }
}