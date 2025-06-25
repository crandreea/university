"use client"

import { Edit, Trash2 } from "lucide-react"
import "../styles/table.css"

export function ProbaTable({ probas }) {
    return (
        <div className="table-container">
            <table className="table">
                <thead className="table-header">
                <tr>
                    <th className="table-cell">ID</th>
                    <th className="table-cell">Cuvinte ghicite</th>
                </tr>
                </thead>
                <tbody>
                {probas.length === 0 ? (
                    <tr className="table-row">
                        <td colSpan={4} className="table-cell empty-message">
                            Nu există înregistrări
                        </td>
                    </tr>
                ) : (
                    probas.map((proba) => (
                        <tr key={proba.id} className={`table-row ${proba.highlighted ? "highlighted" : ""}`}>
                            <td className="table-cell">{proba.noOfGuessedWords}</td>
                        </tr>
                    ))
                )}
                </tbody>
            </table>
        </div>
    )
}
