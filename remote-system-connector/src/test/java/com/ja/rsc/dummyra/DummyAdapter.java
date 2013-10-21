package com.ja.rsc.dummyra;

import javax.resource.spi.Connector;
import javax.resource.spi.TransactionSupport;

import com.ja.rsc.AbstractAdapter;

@Connector( reauthenticationSupport = false, transactionSupport =
             TransactionSupport.TransactionSupportLevel.LocalTransaction)
public class DummyAdapter extends AbstractAdapter {

}
