// src/hooks/useFetchTokenBalance.ts
import { useState } from 'react';
import axios from 'axios';
import { CONFIG } from '../config';

export const useFetchTokenBalance = () => {
	const [tokenBalance, setTokenBalance] = useState(0);

	const fetchTokenBalance = async (
		accountId: string | undefined,
		tokenSymbol: string,
	) => {
		try {
			axios
				.get(`${CONFIG.baseUrl}/get_token_balance/account_id/${accountId}/token_symbol/${tokenSymbol}`)
				.then((response) => {
					const { data } = response;
					setTokenBalance(data.token_balance / 10 ** 9);
				})
				.catch((error) => {
					throw new Error(error || 'Unknown error');
				});
		} catch (error) {
			console.error(`Error fetching ${tokenSymbol} balance:`, error);
		}
	};

	return { tokenBalance, fetchTokenBalance };
};
