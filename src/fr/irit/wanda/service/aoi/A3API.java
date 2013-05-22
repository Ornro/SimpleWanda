package fr.irit.wanda.service.aoi;

import java.security.cert.X509Certificate;

import javax.servlet.http.HttpServletRequest;

import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;

import fr.irit.wanda.dao.UserAO;
import fr.irit.wanda.entities.User;


public class A3API {

	
	private User getCaller(HttpServletRequest request) throws Exception {
        X509Certificate[] certChain = (X509Certificate[])request.getAttribute("javax.servlet.request.X509Certificate");
        if (certChain != null) {
        	
        	X509Certificate cert = certChain[certChain.length-1];
        	X500Name x500name = new JcaX509CertificateHolder(cert).getSubject();
        	RDN cn = x500name.getRDNs(BCStyle.CN)[0];
        	String email = IETFUtils.valueToString(cn.getFirst().getValue());
        	
        	return new UserAO().getUser(email);
		}else
			throw new Exception("You are not logged in. ");
	}
}
