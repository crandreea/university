import React, { useState } from 'react';
import axios from 'axios';
import "../styles/search.css"

function BoatPlacement() {
    const [positions, setPositions] = useState([
        { x: '', y: '' },
        { x: '', y: '' },
        { x: '', y: '' },
    ]);
    const [message, setMessage] = useState('');
    const [messageType, setMessageType] = useState(''); // 'success' or 'error'

    const handlePositionChange = (index, axis, value) => {
        const newPositions = [...positions];
        newPositions[index][axis] = parseInt(value, 10) || ''; // Parse to int, handle empty string
        setPositions(newPositions);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setMessage('');
        setMessageType('');

        const validPositions = positions.every(pos =>
            typeof pos.x === 'number' && !isNaN(pos.x) &&
            typeof pos.y === 'number' && !isNaN(pos.y)
        );

        if (!validPositions) {
            setMessageType('error');
            setMessage('Please enter valid numerical coordinates for all 3 positions.');
            return;
        }

        try {
            const payload = { positions: positions };

            const response = await axios.post('http://localhost:8080/api/games/boats', payload);
            setMessageType('success');
            setMessage(response.data);

            setPositions([
                { x: '', y: '' },
                { x: '', y: '' },
                { x: '', y: '' },
            ]);
        } catch (err) {
            console.error("Error adding boat:", err);
            setMessageType('error');
            setMessage("Failed to add boat. " + (err.response?.data || err.message));
        }
    };

    return (
        <div className="boat-placement">
            <form onSubmit={handleSubmit}>
                <h4>Enter 3 Boat Positions (0-4 for x, 0-4 for y):</h4>
                {positions.map((pos, index) => (
                    <div key={index} className="position-input">
                        <label>
                            Position {index + 1}:
                            X: <input
                            type="number"
                            min="0"
                            max="4"
                            value={pos.x}
                            onChange={(e) => handlePositionChange(index, 'x', e.target.value)}
                            required
                        />
                            Y: <input
                            type="number"
                            min="0"
                            max="4"
                            value={pos.y}
                            onChange={(e) => handlePositionChange(index, 'y', e.target.value)}
                            required
                        />
                        </label>
                    </div>
                ))}
                <button type="submit">Validate & Attempt Boat Placement</button>
            </form>
            {message && <p className={messageType}>{message}</p>}
        </div>
    );
}

export default BoatPlacement;