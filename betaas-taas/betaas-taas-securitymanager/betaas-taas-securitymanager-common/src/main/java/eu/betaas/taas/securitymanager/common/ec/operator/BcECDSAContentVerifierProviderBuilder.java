/**
Copyright 2014-2015 Center for TeleInFrastruktur (CTIF), Aalborg University

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

@author Bayu Anggorojati [ba@es.aau.dk]
Center for TeleInFrastruktur, Aalborg University, Denmark
 */

package eu.betaas.taas.securitymanager.common.ec.operator;

import java.io.IOException;

import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.Signer;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.signers.DSADigestSigner;
import org.bouncycastle.crypto.signers.ECDSASigner;
import org.bouncycastle.crypto.util.PublicKeyFactory;
import org.bouncycastle.operator.DigestAlgorithmIdentifierFinder;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.bc.BcContentVerifierProviderBuilder;

public class BcECDSAContentVerifierProviderBuilder extends
		BcContentVerifierProviderBuilder {

	private DigestAlgorithmIdentifierFinder digestAlgorithmFinder;
	
	public BcECDSAContentVerifierProviderBuilder(DigestAlgorithmIdentifierFinder digestAlgorithmFinder)
	{
		this.digestAlgorithmFinder = digestAlgorithmFinder;
	}
	
	@Override
	protected Signer createSigner(AlgorithmIdentifier sigAlgId)
			throws OperatorCreationException {
		 AlgorithmIdentifier digAlg = digestAlgorithmFinder.find(sigAlgId);
		 Digest dig = digestProvider.get(digAlg);
		return new DSADigestSigner(new ECDSASigner(), dig);
	}

	@Override
	protected AsymmetricKeyParameter extractKeyParameters(
			SubjectPublicKeyInfo publicKeyInfo) throws IOException {
		
		return PublicKeyFactory.createKey(publicKeyInfo);
	}

}
