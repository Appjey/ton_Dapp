import React, { useState } from 'react';
import Grid from '@mui/material/Unstable_Grid2';
import { CONFIG } from '../config';
import { Box, Button, Modal, Stack, Typography } from '@mui/material';
import { useTonConnectUI } from '@tonconnect/ui-react';
import { ToastContainer } from 'react-toastify';
import { useTonConnect } from '../hooks/useTonConnect';
import { useHandleSendJettons } from '../hooks/useHandleSendJettons';
import { useFetchAccountBalance } from '../hooks/useFetchAccountBalance';
import { useGenText } from '../hooks/useGenText';
import { Address } from '@ton/core';
import './input.css';

import axios from 'axios';

type WalletInfoProps = {
	address?: string;
	balance?: number;
	tokenBalance: number;
	accountBalance: number;
	isLoading: boolean;
};

const WalletInfo: React.FC<WalletInfoProps> = ({
	address,
	balance,
	tokenBalance,
	accountBalance,
	isLoading,
}) => {
	const [isDepositOpen, setIsDepositOpen] = useState(false);
	const [isTranscriptionOpen, setIsTranscriptionOpen] = useState(false);
	const [tonConnectUI] = useTonConnectUI();
	const [error, setError] = useState<string | null>(null);
	const { fetchAccountBalance, setAccountBalance } = useFetchAccountBalance();
	const { connected, wallet } = useTonConnect();
	const addr = wallet.account?.address;
	const validAddress = Address.parseRaw(addr).toString();
	const handleDepositOpen = () => setIsDepositOpen((prev) => !prev);
	const [depAmount, setDepAmount] = useState('10');
	const [isLoadingGenText, setIsLoadingGenText] = useState(false);

	const handleTranscriptionOpen = () => setIsTranscriptionOpen((prev) => !prev);
	const { handleSendJettons } = useHandleSendJettons(
		tonConnectUI,
		CONFIG.jettonWalletContract,
		setAccountBalance,
		setError,
	);
	const jettonWalletContract = CONFIG.jettonWalletContract;

	const [textQuestion, settextQuestion] = useState('');
	const [textDocument, settextDocument] = useState('');

	const style = {
		position: 'absolute',
		top: '50%',
		left: '50%',
		transform: 'translate(-50%, -50%)',
		width: 400,
		bgcolor: 'white',
		borderRadius: 4,
		boxShadow: 24,
		p: 4,
	};
	const deductToken = async ({ accountId }: { accountId: string }) => {
		axios
			.post(
				`${CONFIG.baseUrl}/deduct_token`,
				{
					account_id: accountId,
					deduction_amount: 3,
				},
				{
					method: 'POST',
					headers: { 'Content-Type': 'application/json' },
				},
			)
			.then((response) => {
				const { data } = response;

				console.log('Token deducted:', data.new_balance, wallet);
				setAccountBalance(data.new_balance);
			})
			.catch(() => {
				setError('Error deducting token.');
			});
	};

	const { text, fetchText } = useGenText(deductToken, fetchAccountBalance);

	return (
		<Stack alignItems="center">
			<Grid
				container
				direction="row"
				spacing={1}
				justifyContent="flex-start"
				width="100%"
			>
				<Grid xs={4} textAlign="right">
					<Typography variant="h6">Wallet Address:</Typography>
				</Grid>
				<Grid xs={8}>
					<Typography variant="h6">{address}</Typography>
				</Grid>
			</Grid>
			<Grid container direction="row" spacing={1} width="100%">
				<Grid xs={4} textAlign="right">
					<Typography variant="h6">Balance:</Typography>
				</Grid>
				<Grid xs={8}>
					<Typography variant="h6">{`${balance} TON`}</Typography>
				</Grid>
			</Grid>
			{isLoading ? (
				<Typography variant="h6">Loading...</Typography>
			) : (
				<>
					<Grid container direction="row" spacing={1} width="100%">
						<Grid xs={4} textAlign="right">
							<Typography variant="h6">Wallet tokens:</Typography>
						</Grid>
						<Grid xs={8}>
							<Typography variant="h6">{`${tokenBalance} ${CONFIG.tokenSymbol}`}</Typography>
						</Grid>
					</Grid>
					<Grid container direction="row" spacing={1} width="100%">
						<Grid xs={4} textAlign="right">
							<Typography variant="h6">Account tokens:</Typography>
						</Grid>
						<Grid xs={8}>
							<Typography variant="h6">{`${accountBalance} ${CONFIG.tokenSymbol}`}</Typography>
						</Grid>
					</Grid>
				</>
			)}
			<Stack mt={3} width={500} justifyContent="center">
				<Button fullWidth onClick={handleDepositOpen} variant="contained">
					Deposit Tokens
				</Button>
			</Stack>
			<Stack mt={3} width={500} justifyContent="center">
				<Button fullWidth onClick={handleTranscriptionOpen} variant="contained">
					Get Answer
				</Button>
			</Stack>
			{/* Модальное окно депозита */}
			<Modal open={isDepositOpen} onClose={handleDepositOpen}>
				<Box sx={style}>
					<Stack spacing={2}>
						<Typography variant="h5">Deposite tokens</Typography>
						<Typography>Contract Address: </Typography>
						<Typography>{jettonWalletContract}</Typography>
						<input
							className="input1"
							type="text"
							value={depAmount}
							onChange={(e) => setDepAmount(e.target.value)}
							placeholder="Enter deposit amount"
						/>
						{address && (
							<Button
								variant="contained"
								className={`button ${connected ? 'Active' : 'Disabled'}`}
								onClick={() => handleSendJettons(address, depAmount, connected)}
							>
								Deposit Jettons
							</Button>
						)}
						<Button onClick={handleDepositOpen}>Close</Button>
					</Stack>
				</Box>
			</Modal>
			{/* Модальное окно транскрипции */}
			<Modal open={isTranscriptionOpen} onClose={handleTranscriptionOpen}>
				<Box sx={style}>
					<Stack spacing={2}>
						<Typography variant="h5">Get Answer</Typography>
						<p>Enter Question</p>
						<input
							className="input2"
							type="text"
							value={textQuestion}
							onChange={(e) => settextQuestion(e.target.value)}
							placeholder="Enter you Question"
						/>
						<p>Enter Document</p>
						<textarea
							className="document"
							rows={7}
							value={textDocument}
							onChange={(e) => settextDocument(e.target.value)}
							placeholder="Enter the document here"
						/>
						<div>{isLoadingGenText ? <span>Loading...</span> : <p>{text}</p>}</div>
						{address && (
							<Button
								variant="contained"
								onClick={() => {
									setIsLoadingGenText(true);
									fetchText(address, textQuestion, textDocument).then(() => {
										setIsLoadingGenText(false);
									});
								}}
							>
								Pay and get answer
							</Button>
						)}
						<Button onClick={handleTranscriptionOpen}>Close</Button>
					</Stack>
				</Box>
			</Modal>
			<ToastContainer />
		</Stack>
	);
};

export default WalletInfo;
