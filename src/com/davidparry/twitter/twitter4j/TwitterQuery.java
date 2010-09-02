/*
Copyright (c) 2009-2010, David Parry
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
    * Neither the name of the David Parry nor the
      names of its contributors may be used to endorse or promote products
      derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY David Parry ``AS IS'' AND ANY
EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL David Parry BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package com.davidparry.twitter.twitter4j;

import java.util.List;

import android.util.Log;

import com.davidparry.twitter.common.Util;
import com.davidparry.twitter.exception.QueryValidationException;

public class TwitterQuery {
	private static final String TAG = "TwitterQuery";
	
	private String latLong;
	private String orTerms;
	private String andTerms;
	private List<String> andList;
	private List<String> orList;
	private List<String> xlist;
	
	
	public void validate() throws QueryValidationException{
		boolean valid = false;
		if(this.andTerms != null && this.andTerms.length() >0){
			this.andList = Util.getTermsList(this.andTerms);
		}
		if(this.orTerms != null && this.orTerms.length() >0){
			this.orList = Util.getTermsList(this.orTerms);
		}
		if(andList != null && !andList.isEmpty()){
			valid = true;
		}
		if(orList != null && !orList.isEmpty()){
			valid = true;
		}
		if(!valid){
			throw new QueryValidationException("Must have a And or an Or term filled in.");
		}
	}
	public TwitterQuery(String andList,String orList,String xList){
		this.andList = Util.getTermsList(andList);
		this.orList = Util.getTermsList(orList);
		this.xlist = Util.getTermsList(xList);
	};
	
	public TwitterQuery(){};
	public TwitterQuery(String orTerms, String andTerms) {
		this.orTerms = orTerms;
		this.andTerms = andTerms;
	}
	
	
	public String getNonValidatedQuery() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(createQuestion(this.andList, this.orList, this.xlist));
		String s = buffer.toString();
		Log.d(TAG,"Unvalidated Query:: "+ s);
		return s;
	}
	
	public String getQuery() throws QueryValidationException{
		validate();
		return getNonValidatedQuery();
	}
	private String createQuestion(List<String> interms,List<String> onterms,List<String> exterms){
		StringBuffer buf = new StringBuffer();
		if(interms != null && interms.size() >0){
			int i =0;
			//buf.append("&ands=");
			for(String s: interms){
				if(i >0){
					//buf.append("+");
					buf.append("+AND+");
				}
				buf.append(s);
				i++;
			}
		}
		if(onterms != null && onterms.size() >0){
			int i =0;
			//buf.append("&ors=");
			for(String s: onterms){
				if(i >0){
					//buf.append("+");
					buf.append("+OR+");
				}
				buf.append(s);
				i++;
			}
		}
		if(exterms != null && exterms.size() >0){
			int i =0;
			//buf.append("&nots=");
			buf.append("-");
			for(String s: exterms){
				if(i >0){
					//buf.append("+");
				}
				if(i>1){
					buf.append("-");
				}
				buf.append(s);
				i++;
			}
		}
		return buf.toString();
	}
	public String getLatLong() {
		return latLong;
	}
	public void setLatLong(String latLong) {
		this.latLong = latLong;
	}
	public String getOrTerms() {
		return orTerms;
	}
	public void setOrTerms(String orTerms) {
		this.orTerms = orTerms;
	}
	public String getAndTerms() {
		return andTerms;
	}
	public void setAndTerms(String andTerms) {
		this.andTerms = andTerms;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((andList == null) ? 0 : andList.hashCode());
		result = prime * result
				+ ((andTerms == null) ? 0 : andTerms.hashCode());
		result = prime * result + ((latLong == null) ? 0 : latLong.hashCode());
		result = prime * result + ((orList == null) ? 0 : orList.hashCode());
		result = prime * result + ((orTerms == null) ? 0 : orTerms.hashCode());
		result = prime * result + ((xlist == null) ? 0 : xlist.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TwitterQuery other = (TwitterQuery) obj;
		if (andList == null) {
			if (other.andList != null)
				return false;
		} else if (!andList.equals(other.andList))
			return false;
		if (andTerms == null) {
			if (other.andTerms != null)
				return false;
		} else if (!andTerms.equals(other.andTerms))
			return false;
		if (latLong == null) {
			if (other.latLong != null)
				return false;
		} else if (!latLong.equals(other.latLong))
			return false;
		if (orList == null) {
			if (other.orList != null)
				return false;
		} else if (!orList.equals(other.orList))
			return false;
		if (orTerms == null) {
			if (other.orTerms != null)
				return false;
		} else if (!orTerms.equals(other.orTerms))
			return false;
		if (xlist == null) {
			if (other.xlist != null)
				return false;
		} else if (!xlist.equals(other.xlist))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TwitterQuery [andList=" + andList + ", andTerms=" + andTerms
				+ ", latLong=" + latLong + ", orList=" + orList + ", orTerms="
				+ orTerms + ", xlist=" + xlist + "]";
	}
	
		
	
}
