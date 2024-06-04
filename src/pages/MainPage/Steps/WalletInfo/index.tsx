import { FC, useState, useEffect } from 'react';
import { useTonWallet } from '@tonconnect/ui-react';
import { Address } from '@ton/core';
import { StepType } from '../..';
import WalletInfo from '../../../../components/WalletInfo';
import { useFetchAccountBalance } from '../../../../hooks/useFetchAccountBalance';
import { useFetchTokenBalance } from '../../../../hooks/useFetchTokenBalance';
import { CONFIG } from '../../../../config';
import { Box } from '@mui/material';

import axios from 'axios';

type Props = {
	setStep: (step: StepType) => void;
};

const WalletInfoStep: FC<Props> = ({ setStep }) => {
	const wallet = String(useTonWallet()?.account?.address);
	const validAddress = Address.parseRaw(wallet).toString();
	const [isLoading, setIsLoading] = useState(false);
	const { accountBalance, tonBalance, fetchAccountBalance } = useFetchAccountBalance();
	const { tokenBalance, fetchTokenBalance } = useFetchTokenBalance();

	useEffect(() => {
		axios
			.post(
				`${CONFIG.baseUrl}/user`,
				{ account_id: validAddress },
				{
					headers: { 'Content-Type': 'application/json' },
				},
			)
			.then(() => {})
			.catch(() => {
				alert('registred');
			});

		fetchAccountBalance(validAddress);
		fetchTokenBalance(validAddress, CONFIG.tokenSymbol);
	}, [validAddress]);

	return (
		<Box p={5} bgcolor="white" borderRadius={4}>
			{/* Wallet Information */}
			{wallet && (
				<WalletInfo
					address={validAddress}
					balance={tonBalance}
					tokenBalance={tokenBalance}
					accountBalance={accountBalance}
					isLoading={isLoading}
				/>
			)}
		</Box>
	);
};

export default WalletInfoStep;
