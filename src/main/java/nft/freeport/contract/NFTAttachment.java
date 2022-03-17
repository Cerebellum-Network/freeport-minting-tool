package nft.freeport.contract;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.4.1.
 */
@SuppressWarnings("rawtypes")
public class NFTAttachment extends Contract {
    public static final String BINARY = "0x608060405234801561001057600080fd5b506114ff806100206000396000f3fe6080604052600436106100ce5760003560e01c806301ffc9a7146100d3578063248a9ca3146101085780632f2ff15d1461013657806336568abe146101585780633659cfe6146101785780634f1ef28614610198578063572b6c05146101ab57806391d14854146101cb5780639470ad85146101eb5780639a86e8ce14610218578063a217fddf1461023a578063b85f8ca91461024f578063c0ba9f551461026f578063c4d66de81461028f578063d2e9277b146102af578063d547741f146102d0578063f9715b8f146102f0575b600080fd5b3480156100df57600080fd5b506100f36100ee36600461116d565b610310565b60405190151581526020015b60405180910390f35b34801561011457600080fd5b50610128610123366004611126565b610347565b6040519081526020016100ff565b34801561014257600080fd5b5061015661015136600461113e565b61035c565b005b34801561016457600080fd5b5061015661017336600461113e565b610385565b34801561018457600080fd5b5061015661019336600461104c565b610418565b6101566101a6366004611068565b61043f565b3480156101b757600080fd5b506100f36101c636600461104c565b610454565b3480156101d757600080fd5b506100f36101e636600461113e565b61046a565b3480156101f757600080fd5b5060fb5461020b906001600160a01b031681565b6040516100ff91906112ae565b34801561022457600080fd5b506101286000805160206114aa83398151915281565b34801561024657600080fd5b50610128600081565b34801561025b57600080fd5b5061015661026a3660046111ad565b610495565b34801561027b57600080fd5b5061015661028a3660046111ad565b610566565b34801561029b57600080fd5b506101566102aa36600461104c565b610698565b3480156102bb57600080fd5b5061020b6102ca366004611126565b60601c90565b3480156102dc57600080fd5b506101566102eb36600461113e565b61073a565b3480156102fc57600080fd5b5061015661030b3660046111ad565b610759565b60006001600160e01b03198216637965db0b60e01b148061034157506301ffc9a760e01b6001600160e01b03198316145b92915050565b600090815260c9602052604090206001015490565b61036582610347565b610376816103716107cc565b6107ee565b6103808383610852565b505050565b61038d6107cc565b6001600160a01b0316816001600160a01b03161461040a5760405162461bcd60e51b815260206004820152602f60248201527f416363657373436f6e74726f6c3a2063616e206f6e6c792072656e6f756e636560448201526e103937b632b9903337b91039b2b63360891b60648201526084015b60405180910390fd5b61041482826108d9565b5050565b6104218161095e565b61043c816040518060200160405280600081525060006109a2565b50565b6104488261095e565b610414828260016109a2565b60006103416000805160206114aa833981519152835b600091825260c9602090815260408084206001600160a01b0393909316845291905290205460ff1690565b826104b25760405162461bcd60e51b815260040161040190611372565b60006104bc6107cc565b905060006104ca8560601c90565b9050806001600160a01b0316826001600160a01b03161461051b5760405162461bcd60e51b815260206004820152600b60248201526a27b7363c9036b4b73a32b960a91b6044820152606401610401565b84826001600160a01b03167f5dc5ea79bba163c4e3f9a2bb5350dbe8490685a9191001c5b4422b37f880d32286866040516105579291906112c2565b60405180910390a35050505050565b826105835760405162461bcd60e51b815260040161040190611372565b600061058d6107cc565b60fb54604051627eeac760e11b81526001600160a01b038084166004830152602482018890529293506000929091169062fdd58e9060440160206040518083038186803b1580156105dd57600080fd5b505afa1580156105f1573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906106159190611195565b90506000811161065c5760405162461bcd60e51b815260206004820152601260248201527127b7363c9031bab93932b73a1037bbb732b960711b6044820152606401610401565b84826001600160a01b03167f70b7e5963956c4cbfbe79063259fc0996d3333c155342ae9110ca45e85995a5586866040516105579291906112c2565b600054610100900460ff16806106b1575060005460ff16155b6106cd5760405162461bcd60e51b815260040161040190611324565b600054610100900460ff161580156106ef576000805461ffff19166101011790555b6106f7610b1e565b6001600160a01b03821661070a57600080fd5b60fb80546001600160a01b0319166001600160a01b0384161790558015610414576000805461ff00191690555050565b61074382610347565b61074f816103716107cc565b61038083836108d9565b826107765760405162461bcd60e51b815260040161040190611372565b60006107806107cc565b905083816001600160a01b03167f4d1230ca9d634685a91b90e38709c77299e0c7591f7ac427d6cd88cf0f6114bf85856040516107be9291906112c2565b60405180910390a350505050565b60006107d733610454565b156107e9575060131936013560601c90565b503390565b6107f8828261046a565b61041457610810816001600160a01b03166014610b28565b61081b836020610b28565b60405160200161082c92919061123f565b60408051601f198184030181529082905262461bcd60e51b8252610401916004016112f1565b61085c828261046a565b61041457600082815260c9602090815260408083206001600160a01b03851684529091529020805460ff191660011790556108956107cc565b6001600160a01b0316816001600160a01b0316837f2f8788117e7eff1d82e926ec794901d17c78024a50270940304540a733656f0d60405160405180910390a45050565b6108e3828261046a565b1561041457600082815260c9602090815260408083206001600160a01b03851684529091529020805460ff1916905561091a6107cc565b6001600160a01b0316816001600160a01b0316837ff6391f5c32d9c69d2a47ea670b442974b53935d1edc7fd64eb21e047a839171b60405160405180910390a45050565b61096960003361046a565b61043c5760405162461bcd60e51b815260206004820152600a60248201526927b7363c9020b236b4b760b11b6044820152606401610401565b60006109ac610d10565b90506109b784610d2c565b6000835111806109c45750815b156109d5576109d38484610dbf565b505b7f4910fdfa16fed3260ed0e7147f7cc6da11a60208b5b9406d12a635614ffd9143805460ff16610b1757805460ff19166001178155604051610a50908690610a219085906024016112ae565b60408051601f198184030181529190526020810180516001600160e01b0316631b2ce7f360e11b179052610dbf565b50805460ff19168155610a61610d10565b6001600160a01b0316826001600160a01b031614610ad95760405162461bcd60e51b815260206004820152602f60248201527f45524331393637557067726164653a207570677261646520627265616b73206660448201526e75727468657220757067726164657360881b6064820152608401610401565b610ae285610d2c565b6040516001600160a01b038616907fbc7cd75a20ee27fd9adebab32041f755214dbc6bffa90cc0225b39da2e5c2d3b90600090a25b5050505050565b610b26610eaa565b565b60606000610b378360026113bb565b610b429060026113a3565b6001600160401b03811115610b6757634e487b7160e01b600052604160045260246000fd5b6040519080825280601f01601f191660200182016040528015610b91576020820181803683370190505b509050600360fc1b81600081518110610bba57634e487b7160e01b600052603260045260246000fd5b60200101906001600160f81b031916908160001a905350600f60fb1b81600181518110610bf757634e487b7160e01b600052603260045260246000fd5b60200101906001600160f81b031916908160001a9053506000610c1b8460026113bb565b610c269060016113a3565b90505b6001811115610cba576f181899199a1a9b1b9c1cb0b131b232b360811b85600f1660108110610c6857634e487b7160e01b600052603260045260246000fd5b1a60f81b828281518110610c8c57634e487b7160e01b600052603260045260246000fd5b60200101906001600160f81b031916908160001a90535060049490941c93610cb38161140a565b9050610c29565b508315610d095760405162461bcd60e51b815260206004820181905260248201527f537472696e67733a20686578206c656e67746820696e73756666696369656e746044820152606401610401565b9392505050565b600080516020611463833981519152546001600160a01b031690565b803b610d905760405162461bcd60e51b815260206004820152602d60248201527f455243313936373a206e657720696d706c656d656e746174696f6e206973206e60448201526c1bdd08184818dbdb9d1c9858dd609a1b6064820152608401610401565b60008051602061146383398151915280546001600160a01b0319166001600160a01b0392909216919091179055565b6060823b610e1e5760405162461bcd60e51b815260206004820152602660248201527f416464726573733a2064656c65676174652063616c6c20746f206e6f6e2d636f6044820152651b9d1c9858dd60d21b6064820152608401610401565b600080846001600160a01b031684604051610e399190611223565b600060405180830381855af49150503d8060008114610e74576040519150601f19603f3d011682016040523d82523d6000602084013e610e79565b606091505b5091509150610ea1828260405180606001604052806027815260200161148360279139610ec5565b95945050505050565b610eb2610efe565b610eba610f79565b610b26600033610fd8565b60608315610ed4575081610d09565b825115610ee45782518084602001fd5b8160405162461bcd60e51b815260040161040191906112f1565b600054610100900460ff1680610f17575060005460ff16155b610f335760405162461bcd60e51b815260040161040190611324565b600054610100900460ff16158015610f55576000805461ffff19166101011790555b610f5d610fe2565b610f65610fe2565b801561043c576000805461ff001916905550565b600054610100900460ff1680610f92575060005460ff16155b610fae5760405162461bcd60e51b815260040161040190611324565b600054610100900460ff16158015610fd0576000805461ffff19166101011790555b610f55610fe2565b6104148282610852565b600054610100900460ff1680610ffb575060005460ff16155b6110175760405162461bcd60e51b815260040161040190611324565b600054610100900460ff16158015610f65576000805461ffff1916610101179055801561043c576000805461ff001916905550565b60006020828403121561105d578081fd5b8135610d098161144d565b6000806040838503121561107a578081fd5b82356110858161144d565b915060208301356001600160401b03808211156110a0578283fd5b818501915085601f8301126110b3578283fd5b8135818111156110c5576110c5611437565b604051601f8201601f19908116603f011681019083821181831017156110ed576110ed611437565b81604052828152886020848701011115611105578586fd5b82602086016020830137856020848301015280955050505050509250929050565b600060208284031215611137578081fd5b5035919050565b60008060408385031215611150578182fd5b8235915060208301356111628161144d565b809150509250929050565b60006020828403121561117e578081fd5b81356001600160e01b031981168114610d09578182fd5b6000602082840312156111a6578081fd5b5051919050565b6000806000604084860312156111c1578081fd5b8335925060208401356001600160401b03808211156111de578283fd5b818601915086601f8301126111f1578283fd5b8135818111156111ff578384fd5b876020828501011115611210578384fd5b6020830194508093505050509250925092565b600082516112358184602087016113da565b9190910192915050565b76020b1b1b2b9b9a1b7b73a3937b61d1030b1b1b7bab73a1604d1b8152600083516112718160178501602088016113da565b7001034b99036b4b9b9b4b733903937b6329607d1b60179184019182015283516112a28160288401602088016113da565b01602801949350505050565b6001600160a01b0391909116815260200190565b60208152816020820152818360408301376000818301604090810191909152601f909201601f19160101919050565b60208152600082518060208401526113108160408501602087016113da565b601f01601f19169190910160400192915050565b6020808252602e908201527f496e697469616c697a61626c653a20636f6e747261637420697320616c72656160408201526d191e481a5b9a5d1a585b1a5e995960921b606082015260800190565b6020808252601790820152760c081a5cc81b9bdd0818481d985b1a5908139195081251604a1b604082015260600190565b600082198211156113b6576113b6611421565b500190565b60008160001904831182151516156113d5576113d5611421565b500290565b60005b838110156113f55781810151838201526020016113dd565b83811115611404576000848401525b50505050565b60008161141957611419611421565b506000190190565b634e487b7160e01b600052601160045260246000fd5b634e487b7160e01b600052604160045260246000fd5b6001600160a01b038116811461043c57600080fdfe360894a13ba1a3210667c828492db98dca3e2076cc3735a920a3ca505d382bbc416464726573733a206c6f772d6c6576656c2064656c65676174652063616c6c206661696c65643d2e894c222ba979e2dce2c7d940c0e9bb14306669e9f034eb1bb3979a2069d8a26469706673582212209d7e12b01fdba77ac699c9ee5a0b26a55ed7b1e6274a5bed04fdb3b98173e29264736f6c63430008040033";

    public static final String FUNC_DEFAULT_ADMIN_ROLE = "DEFAULT_ADMIN_ROLE";

    public static final String FUNC_META_TX_FORWARDER = "META_TX_FORWARDER";

    public static final String FUNC_FREEPORT = "freeport";

    public static final String FUNC_GETROLEADMIN = "getRoleAdmin";

    public static final String FUNC_GRANTROLE = "grantRole";

    public static final String FUNC_HASROLE = "hasRole";

    public static final String FUNC_ISTRUSTEDFORWARDER = "isTrustedForwarder";

    public static final String FUNC_RENOUNCEROLE = "renounceRole";

    public static final String FUNC_REVOKEROLE = "revokeRole";

    public static final String FUNC_SUPPORTSINTERFACE = "supportsInterface";

    public static final String FUNC_UPGRADETO = "upgradeTo";

    public static final String FUNC_UPGRADETOANDCALL = "upgradeToAndCall";

    public static final String FUNC_INITIALIZE = "initialize";

    public static final String FUNC_MINTERATTACHTONFT = "minterAttachToNFT";

    public static final String FUNC_OWNERATTACHTONFT = "ownerAttachToNFT";

    public static final String FUNC_ANONYMATTACHTONFT = "anonymAttachToNFT";

    public static final String FUNC__MINTERFROMNFTID = "_minterFromNftId";

    public static final Event ADMINCHANGED_EVENT = new Event("AdminChanged", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}));
    ;

    public static final Event ANONYMATTACHTONFT_EVENT = new Event("AnonymAttachToNFT", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>(true) {}, new TypeReference<DynamicBytes>() {}));
    ;

    public static final Event BEACONUPGRADED_EVENT = new Event("BeaconUpgraded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}));
    ;

    public static final Event MINTERATTACHTONFT_EVENT = new Event("MinterAttachToNFT", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>(true) {}, new TypeReference<DynamicBytes>() {}));
    ;

    public static final Event OWNERATTACHTONFT_EVENT = new Event("OwnerAttachToNFT", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>(true) {}, new TypeReference<DynamicBytes>() {}));
    ;

    public static final Event ROLEADMINCHANGED_EVENT = new Event("RoleAdminChanged", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Bytes32>(true) {}, new TypeReference<Bytes32>(true) {}));
    ;

    public static final Event ROLEGRANTED_EVENT = new Event("RoleGranted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event ROLEREVOKED_EVENT = new Event("RoleRevoked", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event UPGRADED_EVENT = new Event("Upgraded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}));
    ;

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
        _addresses.put("80001", "0x84766787c6b9131927A76634F7DDCfcf3ff2e9d1");
        _addresses.put("137", "0x651f2C6942F1c290632Ad5bB61D9ece789f82f35");
    }

    @Deprecated
    protected NFTAttachment(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected NFTAttachment(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected NFTAttachment(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected NFTAttachment(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<AdminChangedEventResponse> getAdminChangedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ADMINCHANGED_EVENT, transactionReceipt);
        ArrayList<AdminChangedEventResponse> responses = new ArrayList<AdminChangedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            AdminChangedEventResponse typedResponse = new AdminChangedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.previousAdmin = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.newAdmin = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<AdminChangedEventResponse> adminChangedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, AdminChangedEventResponse>() {
            @Override
            public AdminChangedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ADMINCHANGED_EVENT, log);
                AdminChangedEventResponse typedResponse = new AdminChangedEventResponse();
                typedResponse.log = log;
                typedResponse.previousAdmin = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.newAdmin = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<AdminChangedEventResponse> adminChangedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ADMINCHANGED_EVENT));
        return adminChangedEventFlowable(filter);
    }

    public List<AnonymAttachToNFTEventResponse> getAnonymAttachToNFTEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ANONYMATTACHTONFT_EVENT, transactionReceipt);
        ArrayList<AnonymAttachToNFTEventResponse> responses = new ArrayList<AnonymAttachToNFTEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            AnonymAttachToNFTEventResponse typedResponse = new AnonymAttachToNFTEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.anonym = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.nftId = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.attachment = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<AnonymAttachToNFTEventResponse> anonymAttachToNFTEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, AnonymAttachToNFTEventResponse>() {
            @Override
            public AnonymAttachToNFTEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ANONYMATTACHTONFT_EVENT, log);
                AnonymAttachToNFTEventResponse typedResponse = new AnonymAttachToNFTEventResponse();
                typedResponse.log = log;
                typedResponse.anonym = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.nftId = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.attachment = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<AnonymAttachToNFTEventResponse> anonymAttachToNFTEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ANONYMATTACHTONFT_EVENT));
        return anonymAttachToNFTEventFlowable(filter);
    }

    public List<BeaconUpgradedEventResponse> getBeaconUpgradedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(BEACONUPGRADED_EVENT, transactionReceipt);
        ArrayList<BeaconUpgradedEventResponse> responses = new ArrayList<BeaconUpgradedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            BeaconUpgradedEventResponse typedResponse = new BeaconUpgradedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.beacon = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<BeaconUpgradedEventResponse> beaconUpgradedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, BeaconUpgradedEventResponse>() {
            @Override
            public BeaconUpgradedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(BEACONUPGRADED_EVENT, log);
                BeaconUpgradedEventResponse typedResponse = new BeaconUpgradedEventResponse();
                typedResponse.log = log;
                typedResponse.beacon = (String) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<BeaconUpgradedEventResponse> beaconUpgradedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(BEACONUPGRADED_EVENT));
        return beaconUpgradedEventFlowable(filter);
    }

    public List<MinterAttachToNFTEventResponse> getMinterAttachToNFTEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(MINTERATTACHTONFT_EVENT, transactionReceipt);
        ArrayList<MinterAttachToNFTEventResponse> responses = new ArrayList<MinterAttachToNFTEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            MinterAttachToNFTEventResponse typedResponse = new MinterAttachToNFTEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.minter = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.nftId = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.attachment = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<MinterAttachToNFTEventResponse> minterAttachToNFTEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, MinterAttachToNFTEventResponse>() {
            @Override
            public MinterAttachToNFTEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(MINTERATTACHTONFT_EVENT, log);
                MinterAttachToNFTEventResponse typedResponse = new MinterAttachToNFTEventResponse();
                typedResponse.log = log;
                typedResponse.minter = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.nftId = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.attachment = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<MinterAttachToNFTEventResponse> minterAttachToNFTEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(MINTERATTACHTONFT_EVENT));
        return minterAttachToNFTEventFlowable(filter);
    }

    public List<OwnerAttachToNFTEventResponse> getOwnerAttachToNFTEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(OWNERATTACHTONFT_EVENT, transactionReceipt);
        ArrayList<OwnerAttachToNFTEventResponse> responses = new ArrayList<OwnerAttachToNFTEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OwnerAttachToNFTEventResponse typedResponse = new OwnerAttachToNFTEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.nftId = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.attachment = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OwnerAttachToNFTEventResponse> ownerAttachToNFTEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, OwnerAttachToNFTEventResponse>() {
            @Override
            public OwnerAttachToNFTEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(OWNERATTACHTONFT_EVENT, log);
                OwnerAttachToNFTEventResponse typedResponse = new OwnerAttachToNFTEventResponse();
                typedResponse.log = log;
                typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.nftId = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.attachment = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OwnerAttachToNFTEventResponse> ownerAttachToNFTEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OWNERATTACHTONFT_EVENT));
        return ownerAttachToNFTEventFlowable(filter);
    }

    public List<RoleAdminChangedEventResponse> getRoleAdminChangedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ROLEADMINCHANGED_EVENT, transactionReceipt);
        ArrayList<RoleAdminChangedEventResponse> responses = new ArrayList<RoleAdminChangedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RoleAdminChangedEventResponse typedResponse = new RoleAdminChangedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.role = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.previousAdminRole = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.newAdminRole = (byte[]) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<RoleAdminChangedEventResponse> roleAdminChangedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, RoleAdminChangedEventResponse>() {
            @Override
            public RoleAdminChangedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ROLEADMINCHANGED_EVENT, log);
                RoleAdminChangedEventResponse typedResponse = new RoleAdminChangedEventResponse();
                typedResponse.log = log;
                typedResponse.role = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.previousAdminRole = (byte[]) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.newAdminRole = (byte[]) eventValues.getIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<RoleAdminChangedEventResponse> roleAdminChangedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ROLEADMINCHANGED_EVENT));
        return roleAdminChangedEventFlowable(filter);
    }

    public List<RoleGrantedEventResponse> getRoleGrantedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ROLEGRANTED_EVENT, transactionReceipt);
        ArrayList<RoleGrantedEventResponse> responses = new ArrayList<RoleGrantedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RoleGrantedEventResponse typedResponse = new RoleGrantedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.role = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.account = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.sender = (String) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<RoleGrantedEventResponse> roleGrantedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, RoleGrantedEventResponse>() {
            @Override
            public RoleGrantedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ROLEGRANTED_EVENT, log);
                RoleGrantedEventResponse typedResponse = new RoleGrantedEventResponse();
                typedResponse.log = log;
                typedResponse.role = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.account = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.sender = (String) eventValues.getIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<RoleGrantedEventResponse> roleGrantedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ROLEGRANTED_EVENT));
        return roleGrantedEventFlowable(filter);
    }

    public List<RoleRevokedEventResponse> getRoleRevokedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ROLEREVOKED_EVENT, transactionReceipt);
        ArrayList<RoleRevokedEventResponse> responses = new ArrayList<RoleRevokedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RoleRevokedEventResponse typedResponse = new RoleRevokedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.role = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.account = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.sender = (String) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<RoleRevokedEventResponse> roleRevokedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, RoleRevokedEventResponse>() {
            @Override
            public RoleRevokedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ROLEREVOKED_EVENT, log);
                RoleRevokedEventResponse typedResponse = new RoleRevokedEventResponse();
                typedResponse.log = log;
                typedResponse.role = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.account = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.sender = (String) eventValues.getIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<RoleRevokedEventResponse> roleRevokedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ROLEREVOKED_EVENT));
        return roleRevokedEventFlowable(filter);
    }

    public List<UpgradedEventResponse> getUpgradedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(UPGRADED_EVENT, transactionReceipt);
        ArrayList<UpgradedEventResponse> responses = new ArrayList<UpgradedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            UpgradedEventResponse typedResponse = new UpgradedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.implementation = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<UpgradedEventResponse> upgradedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, UpgradedEventResponse>() {
            @Override
            public UpgradedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(UPGRADED_EVENT, log);
                UpgradedEventResponse typedResponse = new UpgradedEventResponse();
                typedResponse.log = log;
                typedResponse.implementation = (String) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<UpgradedEventResponse> upgradedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(UPGRADED_EVENT));
        return upgradedEventFlowable(filter);
    }

    public RemoteFunctionCall<byte[]> DEFAULT_ADMIN_ROLE() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_DEFAULT_ADMIN_ROLE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<byte[]> META_TX_FORWARDER() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_META_TX_FORWARDER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<String> freeport() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_FREEPORT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<byte[]> getRoleAdmin(byte[] role) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETROLEADMIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(role)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<TransactionReceipt> grantRole(byte[] role, String account) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_GRANTROLE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(role), 
                new org.web3j.abi.datatypes.Address(account)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> hasRole(byte[] role, String account) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_HASROLE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(role), 
                new org.web3j.abi.datatypes.Address(account)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> isTrustedForwarder(String forwarder) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISTRUSTEDFORWARDER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(forwarder)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> renounceRole(byte[] role, String account) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_RENOUNCEROLE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(role), 
                new org.web3j.abi.datatypes.Address(account)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> revokeRole(byte[] role, String account) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REVOKEROLE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(role), 
                new org.web3j.abi.datatypes.Address(account)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> supportsInterface(byte[] interfaceId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_SUPPORTSINTERFACE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes4(interfaceId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> upgradeTo(String newImplementation) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_UPGRADETO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(newImplementation)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> upgradeToAndCall(String newImplementation, byte[] data, BigInteger weiValue) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_UPGRADETOANDCALL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(newImplementation), 
                new org.web3j.abi.datatypes.DynamicBytes(data)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<TransactionReceipt> initialize(String _freeport) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_INITIALIZE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_freeport)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> minterAttachToNFT(BigInteger nftId, byte[] attachment) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_MINTERATTACHTONFT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(nftId), 
                new org.web3j.abi.datatypes.DynamicBytes(attachment)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> ownerAttachToNFT(BigInteger nftId, byte[] attachment) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_OWNERATTACHTONFT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(nftId), 
                new org.web3j.abi.datatypes.DynamicBytes(attachment)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> anonymAttachToNFT(BigInteger nftId, byte[] attachment) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ANONYMATTACHTONFT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(nftId), 
                new org.web3j.abi.datatypes.DynamicBytes(attachment)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> _minterFromNftId(BigInteger id) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC__MINTERFROMNFTID, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(id)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    @Deprecated
    public static NFTAttachment load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new NFTAttachment(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static NFTAttachment load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new NFTAttachment(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static NFTAttachment load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new NFTAttachment(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static NFTAttachment load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new NFTAttachment(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<NFTAttachment> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(NFTAttachment.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<NFTAttachment> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(NFTAttachment.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<NFTAttachment> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(NFTAttachment.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<NFTAttachment> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(NFTAttachment.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static class AdminChangedEventResponse extends BaseEventResponse {
        public String previousAdmin;

        public String newAdmin;
    }

    public static class AnonymAttachToNFTEventResponse extends BaseEventResponse {
        public String anonym;

        public BigInteger nftId;

        public byte[] attachment;
    }

    public static class BeaconUpgradedEventResponse extends BaseEventResponse {
        public String beacon;
    }

    public static class MinterAttachToNFTEventResponse extends BaseEventResponse {
        public String minter;

        public BigInteger nftId;

        public byte[] attachment;
    }

    public static class OwnerAttachToNFTEventResponse extends BaseEventResponse {
        public String owner;

        public BigInteger nftId;

        public byte[] attachment;
    }

    public static class RoleAdminChangedEventResponse extends BaseEventResponse {
        public byte[] role;

        public byte[] previousAdminRole;

        public byte[] newAdminRole;
    }

    public static class RoleGrantedEventResponse extends BaseEventResponse {
        public byte[] role;

        public String account;

        public String sender;
    }

    public static class RoleRevokedEventResponse extends BaseEventResponse {
        public byte[] role;

        public String account;

        public String sender;
    }

    public static class UpgradedEventResponse extends BaseEventResponse {
        public String implementation;
    }
}
