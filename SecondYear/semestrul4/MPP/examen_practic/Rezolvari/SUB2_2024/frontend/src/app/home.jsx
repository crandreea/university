import React, { useState } from 'react';
import '../style/home.css';

function Home() {
    const [alias, setAlias] = useState('');
    const [games, setGames] = useState([]);
    const [config, setConfig] =
        useState({
            letters: '',
            word1: '',
            word2: '',
            word3: '',
            word4: '', });
    const [message, setMessage] = useState('');

    const fetchGames = async () => {
        try {
            if(alias == null || Number(alias)){
                throw new Error('Invalid alias!');
            }
            const response = await fetch(`http://localhost:8080/api/games/${alias}`);
            const data = await response.json();
            setGames(data);
            setMessage('');
        } catch (error) {
            setMessage('Eroare la încărcarea jocurilor');
            setGames([]);
        }
    };

    const addConfig = async () => {
        try {
            const response = await fetch(`http://localhost:8080/api/configurations`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(config),
            });

            if (response.ok) {
                setMessage('Configurație adăugată cu succes!');
                setConfig({ letters: '',
                    word1: '',
                    word2: '',
                    word3: '',
                    word4: '', });
            } else {
                setMessage('Eroare la salvarea configurației');
            }
        } catch (error) {
            setMessage('Eroare de rețea');
        }
    };

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setConfig({ ...config, [name]: value });
    };

    return (
        <div className="container">
            <h1>Game Client</h1>

            <div className="section">
                <h2>Caută jocuri după alias</h2>
                <input
                    type="text"
                    placeholder="Alias jucător"
                    value={alias}
                    onChange={e => setAlias(e.target.value)}
                />
                <button onClick={fetchGames}>Caută</button>

                <ul>
                    {games.map(game => (
                        <li key={game.id}>
                            Joc #{game.id}, cuvinte ghicite: {game.noOfGuessedWords}
                        </li>
                    ))}
                </ul>
            </div>

            <div className="section">
                <h2>Adaugă configurație</h2>
                <input
                    type="text"
                    name="letters"
                    placeholder="Șir de litere"
                    value={config.letters}
                    onChange={handleInputChange}
                />
                <input
                    type="text"
                    name="word1"
                    placeholder="Cuvânt 1"
                    value={config.word1}
                    onChange={handleInputChange}
                />
                <input
                    type="text"
                    name="word2"
                    placeholder="Cuvânt 2"
                    value={config.word2}
                    onChange={handleInputChange}
                />
                <input
                    type="text"
                    name="word3"
                    placeholder="Cuvânt 3"
                    value={config.word3}
                    onChange={handleInputChange}
                />
                <input
                    type="text"
                    name="word4"
                    placeholder="Cuvânt 4"
                    value={config.word4}
                    onChange={handleInputChange}
                />
                <button onClick={addConfig}>Trimite</button>
            </div>

            {message && <p className="message">{message}</p>}
        </div>
    );
}

export default Home;
