	private BigDecimal calcularValorPagamento(List<ArquivoCobrancaPagamentoVO> listaAgrupadaPorVerba, 
											  EnumTipoIntegracao enumTipoIntegracao,
											  Connection conn)throws Exception{
		
		//O DEFAULT PARA PAGAMENTO É DÉBITO
		EnumDebitoCredito tipoLancamento = EnumDebitoCredito.DEBITO;
		BigDecimal valorRetorno = BigDecimal.ZERO;
		
		for (ArquivoCobrancaPagamentoVO arquivoCobrancaPagamento: listaAgrupadaPorVerba){
			
            // fix-teste:  Comentário - Apenas testando BRANCHES no git!
            // fix-teste:  Comentário - Continuamos editando na BRANCH!
			tipoLancamento = pagamentoDao.obterTipoLancamentoPorCodigoRetornoFpw(arquivoCobrancaPagamento.getCodigoVerba(), conn);
			
			if (tipoLancamento.equals(EnumDebitoCredito.DEBITO)) { 
				valorRetorno = valorRetorno.add(JobUtils.formatarValorStringToBigDecimal(arquivoCobrancaPagamento.getValor(), enumTipoIntegracao));
			}
			else {
				valorRetorno = valorRetorno.subtract(JobUtils.formatarValorStringToBigDecimal(arquivoCobrancaPagamento.getValor(), enumTipoIntegracao));
			}
		}
	
		return valorRetorno;
	}