"use client"

import { useState } from "react"
import { Search } from "lucide-react"
import toast from 'react-hot-toast'
import "../styles/search.css"

export function ProbaSearch({ onSearchResult }) {
    //const [searchId, setSearchId] = useState("")
    const [alias, setAlias] = useState('');
    const [isLoading, setIsLoading] = useState(false)

    const handleSearch = async () => {
        if (!alias || !String(alias)) {
            toast("Introduceți un alias valid")
            return
        }

        setIsLoading(true)
        try {
            const response = await fetch(`http://localhost:8080/api/games/${alias}`)

            if (!response.ok) {
                if (response.status === 404) {
                    onSearchResult(null)
                    return
                }
                throw new Error("Failed to search")
            }

            const data = await response.json()
            onSearchResult(data)
        } catch (error) {
            toast("Nu s-a putut efectua căutarea. Încercați din nou.")
        } finally {
            setIsLoading(false)
        }
    }

    return (
        <div className="search-container">
            <div className="search-input-container">
                <input
                    className="search-input"
                    type="text"
                    placeholder="Alias jucator"
                    value={alias}
                    onChange={(e) => setAlias(e.target.value)}
                    onKeyDown={(e) => e.key === "Enter" && handleSearch()}
                />
            </div>
            <button className="search-button" onClick={handleSearch} disabled={isLoading}>
                {isLoading ? (
                    <span className="loading-text">Căutare...</span>
                ) : (
                    <>
                        <Search className="search-icon" />
                        Caută
                    </>
                )}
            </button>
        </div>
    )
}
