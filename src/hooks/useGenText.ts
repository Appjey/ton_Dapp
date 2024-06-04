import { useState } from 'react';
import axios from 'axios';
import { CONFIG } from '../config';

export const useGenText = (
	deductToken: ({ accountId }: { accountId: string }) => Promise<void>,
	fetchAccountBalance: (accountId: string) => Promise<void>,
) => {
	const [text, setText] = useState('');

	const fetchText = async (accountId: string, question: string, document: string) => {
		try {
			const response = await axios.post(`${CONFIG.baseUrl}/generate`, {
				account_id: accountId,
				question: question,
				text: document,
			});

			if (response.status === 200) {
				const data = response.data;
				setText(data.message);
				await deductToken({ accountId });
				await fetchAccountBalance(accountId);
			} else {
				throw new Error('Text generation failed');
			}
		} catch (error) {
			console.error('Error fetching text:', error, accountId);
		}
	};

	return { text, fetchText };
};
