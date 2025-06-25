"use client"

import { Edit, Trash2 } from "lucide-react"
import "../styles/table.css"

export function ProbaTable({ probas, onEdit, onDelete }) {
    return (
        <div className="table-container">
            <table className="table">
                <thead className="table-header">
                <tr>
                    <th className="table-cell">ID</th>
                    <th className="table-cell">Tip</th>
                    <th className="table-cell">Categorie Vârstă ID</th>
                    <th className="table-cell table-cell-actions">Acțiuni</th>
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
                            <td className="table-cell">{proba.id}</td>
                            <td className="table-cell">{proba.tip}</td>
                            <td className="table-cell">{proba.varsta?.id || "N/A"}</td>
                            <td className="table-cell table-cell-actions">
                                <div className="actions-container">
                                    <button className="icon-button edit-button" onClick={() => onEdit(proba)}>
                                        <Edit className="icon" />
                                    </button>
                                    <button className="icon-button delete-button" onClick={() => onDelete(proba.id)}>
                                        <Trash2 className="icon" />
                                    </button>
                                </div>
                            </td>
                        </tr>
                    ))
                )}
                </tbody>
            </table>
        </div>
    )
}
