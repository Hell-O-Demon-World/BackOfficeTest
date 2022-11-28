package com.golfzonaca.backoffice.web.controller.profit;

import com.golfzonaca.backoffice.service.company.CompanyService;
import com.golfzonaca.backoffice.service.profit.ProfitService;
import com.golfzonaca.backoffice.service.profit.dto.ProfitDto;
import com.golfzonaca.backoffice.web.controller.profit.dto.ProfitSearchCond;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ProfitController {
    private final CompanyService companyService;
    private final ProfitService profitService;

    @GetMapping("/{companyId}/profits")
    public String profit(@ModelAttribute("period") ProfitSearchCond period, @PathVariable Long companyId, Model model) {
        Map<Integer, ProfitDto> profit = profitService.calculateProfit(companyId, period);
        model.addAttribute("companyName", companyService.findById(companyId).getName());
        model.addAttribute("places", profit);
        return "profit/profit";
    }
}
