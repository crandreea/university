import React, { useState, useEffect } from 'react';
import axios from 'axios';

function PlayerGames({ playerId }) {
    const [games, setGames] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchPlayerGames = async () => {
            try {
                setLoading(true);
                const response = await axios.get(`http://localhost:8080/api/games/player/${playerId}/successful`);
                setGames(response.data);
            } catch (err) {
                console.error("Error fetching player games:", err);
                setError("Failed to load games. " + (err.response?.data || err.message));
            } finally {
                setLoading(false);
            }
        };

        fetchPlayerGames();
    }, [playerId]);

    if (loading) {
        return <p>Loading games...</p>;
    }

    if (error) {
        return <p className="error">{error}</p>;
    }

    if (games.length === 0) {
        return <p>No games with successful shots found for this player.</p>;
    }

    return (
        <div className="player-games">
            {games.map(game => (
                <div key={game.gameId} className="game-card">
                    <h3>Game ID: {game.gameId}</h3>
                    <p>Player: {game.player ? game.player.username : 'N/A'}</p>
                    <p>Total Score: {game.totalScore}</p>
                    <p>Successful Shots: {game.successfulShots}</p>
                    <p>Total Shots: {game.totalShots}</p>
                    <p>Status: {game.status}</p>
                    <p>Start Time: {new Date(game.startTime).toLocaleString()}</p>
                    <p>End Time: {game.endTime ? new Date(game.endTime).toLocaleString() : 'N/A'}</p>

                    <h4>Player Shots (Order Proposed):</h4>
                    {game.playerShots && game.playerShots.length > 0 ? (
                        <ul>
                            {game.playerShots.map((shot, index) => (
                                <li key={index}>
                                    ({shot.x}, {shot.y}) - Status: {shot.status}
                                </li>
                            ))}
                        </ul>
                    ) : (
                        <p>No shots recorded.</p>
                    )}

                    <h4>Boat Positions:</h4>
                    {game.boatPositions && game.boatPositions.length > 0 ? (
                        <ul>
                            {game.boatPositions.map((pos, index) => (
                                <li key={index}>({pos.x}, {pos.y})</li>
                            ))}
                        </ul>
                    ) : (
                        <p>No boat positions found for this game.</p>
                    )}
                </div>
            ))}
        </div>
    );
}

export default PlayerGames;