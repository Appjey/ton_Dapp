// src/hooks/useFetchAccountBalance.ts
import { useState } from 'react';
import { CONFIG } from '../config';
import axios from 'axios';

export const useFetchAccountBalance = () => {
	const [accountBalance, setAccountBalance] = useState(0);
	const [tonBalance, settonBalance] = useState(0);

	const fetchAccountBalance = async (accountId: string) => {
		try {
			await axios.get(`${CONFIG.baseUrl}/user/${accountId}`).then((response) => {
				const data = response.data;
				setAccountBalance(data.account_balance);
				settonBalance(data.ton_balance);
			});
		} catch (error) {
			console.error('Error fetching account balance:', error, accountId);
		}
	};

	return { accountBalance, tonBalance, fetchAccountBalance, setAccountBalance };
};
