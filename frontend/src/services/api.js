import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
});

export const getAllExpenses = (sort) => {
  return api.get('/expenses', { params: { sort } });
};

export const getExpenseById = (id) => {
  return api.get(`/expenses/${id}`);
};

export const createExpense = (data) => {
  return api.post('/expenses', data);
};

export const updateExpense = (id, data) => {
  return api.put(`/expenses/${id}`, data);
};

export const deleteExpense = (id) => {
  return api.delete(`/expenses/${id}`);
};

export const searchExpenses = (title) => {
  return api.get('/expenses/search', { params: { title } });
};

export const filterExpenses = (params) => {
  return api.get('/expenses/filter', { params });
};

export const exportCsv = () => {
  return api.get('/expenses/export/csv', { responseType: 'blob' });
};

export const getBudget = () => {
  return api.get('/budget');
};

export const updateBudget = (data) => {
  return api.put('/budget', data);
};

export const getDashboard = (month, year) => {
  return api.get('/dashboard', { params: { month, year } });
};

export default api;
