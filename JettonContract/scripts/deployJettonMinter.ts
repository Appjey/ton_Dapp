import { toNano } from '@ton/core';
import { JettonMinter } from '../wrappers/JettonMinter';
import { compile, NetworkProvider } from '@ton-community/blueprint';

export async function run(provider: NetworkProvider) {
    const Jetton = provider.open(JettonMinter.createFromConfig({}, await compile('Counter')));

    // await Jetton.sendDeploy(provider.sender(), toNano('0.05'));

    await provider.waitForDeploy(Jetton.address);

    // await Jetton.getJettonData();
}
