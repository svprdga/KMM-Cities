import SwiftUI
import shared

struct ContentView: View {
    @ObservedObject private(set) var viewModel: ViewModel
    
    var body: some View {
        if viewModel.cities.isEmpty {
            ProgressView()
        } else {
            VStack {
                ForEach(viewModel.cities, id: \.self) { city in
                    Text("\(city.city), \(city.country)")
                }
            }
        }
    }
}

extension ContentView {
    class ViewModel: ObservableObject {
        private var apiClient = ApiClient()
        
        @Published var cities = [City]()
        
        init() {
            apiClient.getCities { cities, error in
                DispatchQueue.main.async {
                    if let cities = cities {
                        self.cities = cities
                    } else {
                        print("Error when fetching cities")
                        self.cities = [City]()
                    }
                }
            }
        }
    }
}
