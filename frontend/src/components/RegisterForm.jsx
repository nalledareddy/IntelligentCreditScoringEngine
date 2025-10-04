import React, { useState, useEffect } from 'react'

export default function RegisterForm() {
  const [users, setUsers] = useState([])
  const [loading, setLoading] = useState(false)
  const [form, setForm] = useState({
    name: '',
    age: '',
    income: '',
    loanAmount: '',
    employmentYears: '',
    debt: ''
  })
  const [error, setError] = useState(null)

  useEffect(() => {
    fetch('/api/users')
      .then(r => r.json())
      .then(setUsers)
      .catch(err => console.error(err))
  }, [])

  async function submit(e) {
    e.preventDefault()
    setLoading(true)
    setError(null)
    try {
      const payload = {
        name: form.name,
        age: Number(form.age),
        income: Number(form.income),
        loanAmount: Number(form.loanAmount),
        employmentYears: Number(form.employmentYears),
        debt: Number(form.debt)
      }
      const res = await fetch('/api/users/register', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
      })
      if (!res.ok) {
        const txt = await res.text()
        throw new Error(txt || 'server error')
      }
      const saved = await res.json()
      setUsers(prev => [saved, ...prev])
      setForm({ name: '', age: '', income: '', loanAmount: '', employmentYears: '', debt: '' })
    } catch (err) {
      console.error(err)
      setError(err.message || 'Unknown error')
    } finally {
      setLoading(false)
    }
  }

  return (
    <>
      <form onSubmit={submit} className="space-y-3">
        <div className="grid grid-cols-2 gap-3">
          <input required value={form.name} onChange={e=>setForm({...form,name:e.target.value})} placeholder="Full name" className="p-2 rounded-lg bg-[#071226] border border-gray-700" />
          <input required value={form.age} onChange={e=>setForm({...form,age:e.target.value})} type="number" placeholder="Age" className="p-2 rounded-lg bg-[#071226] border border-gray-700" />
          <input required value={form.income} onChange={e=>setForm({...form,income:e.target.value})} type="number" placeholder="Income (annual)" className="p-2 rounded-lg bg-[#071226] border border-gray-700 col-span-2" />
          <input required value={form.loanAmount} onChange={e=>setForm({...form,loanAmount:e.target.value})} type="number" placeholder="Loan amount" className="p-2 rounded-lg bg-[#071226] border border-gray-700" />
          <input required value={form.employmentYears} onChange={e=>setForm({...form,employmentYears:e.target.value})} type="number" placeholder="Employment years" className="p-2 rounded-lg bg-[#071226] border border-gray-700" />
          <input required value={form.debt} onChange={e=>setForm({...form,debt:e.target.value})} type="number" placeholder="Total debt" className="p-2 rounded-lg bg-[#071226] border border-gray-700 col-span-2" />
        </div>

        <div className="flex items-center gap-3">
          <button disabled={loading} type="submit" className="px-4 py-2 bg-primary rounded-lg text-black font-medium">
            {loading ? 'Saving...' : 'Register & Score'}
          </button>
          {error && <div className="text-sm text-red-400">{error}</div>}
        </div>
      </form>

      <hr className="my-4 border-gray-700"/>

      <div>
        <h3 className="text-lg font-medium mb-2">Saved applicants</h3>
        <div className="space-y-2 max-h-64 overflow-auto">
          {users.map(u => (
            <div key={u.id || Math.random()} className="p-3 rounded-lg bg-[#071226] border border-gray-800 flex justify-between items-center">
              <div>
                <div className="font-semibold">{u.name}</div>
                <div className="text-sm text-gray-400">Age: {u.age} · Income: {u.income?.toLocaleString?.() ?? u.income}</div>
              </div>
              <div className="text-right">
                <div className="text-lg font-semibold">{u.creditScore ?? '-'}</div>
                <div className="text-sm text-gray-500">score</div>
              </div>
            </div>
          ))}
          {users.length === 0 && <div className="text-sm text-gray-400">No applicants yet.</div>}
        </div>
      </div>
    </>
  )
}
