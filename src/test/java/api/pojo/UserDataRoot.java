package api.pojo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDataRoot {
    private Integer page;
    private Integer per_page;
    private Integer total;

    public UserDataRoot(Integer page, Integer per_page, Integer total, Integer total_pages) {
        this.page = page;
        this.per_page = per_page;
        this.total = total;
        this.total_pages = total_pages;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getPer_page() {
        return per_page;
    }

    public Integer getTotal() {
        return total;
    }

    public Integer getTotal_pages() {
        return total_pages;
    }

    public UserDataRoot() {
        super();
    }

    private Integer total_pages;
}
