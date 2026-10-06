	private BigDecimal calcularValorPagamento(List<ArquivoCobrancaPagamentoVO> listaAgrupadaPorVerba, Connection conn)throws Exception{
		
		//O DEFAULT PARA PAGAMENTO É DÉBITO
		EnumDebitoCredito tipoLancamento = EnumDebitoCredito.DEBITO;
		BigDecimal valorRetorno = BigDecimal.ZERO;
		
		for (ArquivoCobrancaPagamentoVO arquivoCobrancaPagamento: listaAgrupadaPorVerba){
			
			tipoLancamento = pagamentoDao.obterTipoLancamentoPorCodigoRetornoValia(arquivoCobrancaPagamento.getCodigoVerba(), conn);
			
			if (tipoLancamento.equals(EnumDebitoCredito.DEBITO)) { 
				valorRetorno = valorRetorno.add(JobUtils.formatarValorStringToBigDecimal(arquivoCobrancaPagamento.getValor(), EnumTipoIntegracao.RETORNO_VALIA));
			} else {
				valorRetorno = valorRetorno.subtract(JobUtils.formatarValorStringToBigDecimal(arquivoCobrancaPagamento.getValor(), EnumTipoIntegracao.RETORNO_VALIA));
			}
		}
	
		return valorRetorno;
	}